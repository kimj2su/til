# Java 기반의 Producer

```java
public class SimpleProducer {
    public static void main(String[] args) {

        String topicName = "simple-topic";

        //KafkaProducer configuration setting
        //null, "hello world"

        Properties props = new Properties();
        //bootstrap.servers, key.serializer.class, value.serializer.class
        props.setProperty("bootstrap.servers", "192.168.64.10:9092");
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.64.10:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //KafkaProducer 객체 생성
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);

        //ProducerRecord 객체 생성
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName,"hello world2");

        //KafkaProducer message send
        kafkaProducer.send(producerRecord);

        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
```

기본적으로 메세지는 flush를 해줘야 메세지가 날라간다.  
여기서 저는 UTM을 사용해서 ubuntu위에 카프카 서버를 띄어 놓고 사용중입니다.  
그냥 VM을 사용하지 않고 로컬에 사용하면 localhost:9092를 통해 통신 할 수 있지만 VM을 통해 사용할때는 카프카서버의 설정을 해줘야 통신 할 수 있습니다.  
kafka 컨슈밍이 안될떄는 advertised.listeners 설정을 확인해야합니다.  
confluent/etc/kafka/server.properties 파일에 카프카 서버에 대한 설정들이 있습니다.  
여기서 listeners 와 advertised.listeners 가 있는데
advertised.listeners는 카프카 프로듀서, 컨슈머에게 노출할 주소 입니다. 설정하지 않을 경우 디폴트로 listeners 설정을 따릅니다.   
기본적으로 두개 모두 주석처리가 되어 있습니다.

## listeners 와 advertised.listeners 가 별도로 존재하는 이유  
만약 우리의 Kafka 서버가 3개의 랜카드를 장착중이고 A,B,C 라는 IP를 각각 부여 받아 사용중이고,  
해당 서버에는 Kafka 서비스와, 그 Kafka의 Topic을 구독중인 별도의 Test라는 서비스가 실행중이라고 생각해봅시다.  
우리의 Test 서비스는 Kafka 서비스와 같은 PC에서 구동중이기에 localhost 또는 127.0.0.1 이라는 주소로 kafka에 접근이 가능합니다.  

하지만 만약 A,B,C 라는 IP로 접근을 하려는 외부 서비스들이 있을 경우 특정 IP로 접근한 요청들은 Kafka에 접근하지 못하게 해야하는 경우가 있습니다.

예를들어 우리의 서버는 localhost로 접근하는 내부 서비스와 B라는 IP로 접근하는 외부 서비스만 Kafka에 접근 할 수 있게 하고 싶은경우
```java
listeners=PLAINTEXT://localhost:9092
advertised.listeners==PLAINTEXT://B:9092
```

라고 지정할 수 있겠습니다.
이렇게 내부와 외부에 오픈할 특정 IP를 별도로 두기 위해서 listeners, advertised.listeners 가 존재합니다.

그래서 저는 
```java
# The address the socket server listens on. It will get the value returned from
# java.net.InetAddress.getCanonicalHostName() if not configured.
#   FORMAT:
#     listeners = listener_name://host_name:port
#   EXAMPLE:
#     listeners = PLAINTEXT://your.host.name:9092
#listeners=PLAINTEXT://:9092

# Hostname and port the broker will advertise to producers and consumers. If not set,
# it uses the value for "listeners" if configured.  Otherwise, it will use the value
# returned from java.net.InetAddress.getCanonicalHostName().
advertised.listeners=PLAINTEXT://192.168.64.10:9092
```
advertised.listeners=PLAINTEXT://192.168.64.10:9092 의 주석을 풀고 ubuntu의 아이피 포트를 설정해 두었습니다.


# Kafka Producer의 send 메소드 호출 프로세스
- Kafka Producer 전송은 Producer Client의 별도의 Thread가 전송을 담당한다는 점에서 기본적으로 Thread간 Async 전송임.
- 즉 Producer Client의 Main Thread가 send() 메소드를 호출하여 메시지 전송을 시작하지만 바로 전송되지 않으며 내부 Buffer에 메시지를 저장 후에 별도의 
Thread가 Kafka Broker에 실제 전송을 하는 방식이다.

# Producer와 브로커와의 메시지 동기화/비동기화 전송
기본적으로 send 메소드는 비동기 방식으로 이루어집니다.  
## Sync(동기화 방식)
- Producer는 브로커로 부터 해당 메시지를 성공적으로 받았다는 Ack 메시지를 받은 후 다음 메시지를 전송
- KafkaProducer.sned().get() 호출하여 브로커로 부터 Ack 메시지를 받을 때 까지 대기(Wait)함.  

## SimpleProducerSync
```
try {
            RecordMetadata recordMetadata = kafkaProducer.send(producerRecord).get();
            logger.info("\n ##### record metadata received ##### \n" +
                    "partition: " + recordMetadata.partition() + "\n" +
                    "offset: " + recordMetadata.offset() + "\n" +
                    "timestamp: " + recordMetadata.timestamp());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            kafkaProducer.close();
}
```
kafkaProducer.send(producerRecord).get();  하게 되면 blocking 방식으로 응답을 기다리게 됩니다.

## Async(비동기 방식)

- Producer는 브로커로부터 해당 메시지를 성공적으로 받았다는 Ack메시지를 기다리지 않고 전송
- 브로커로 부터 Ack 메시지를 비동기로 Producer에 받기 위해서 Callback을 적용함
- send() 메소드 호출 시에 callback 객체를 인자로 입력하여 Ack 메시지를 Producer로 전달 받을 수 있음.

Callback의 이해
다른코드의 인수로서 넘겨주는 실행 가능한 코드이며, 콜백을 넘겨받는 코드는 이 콜백을 필요에 따라 즉시 실행할 수도 있고, 아니면 나중에 실행할 수도 있음.  
즉 Callback은 다른 함수의 인자로서 전달된 후에 특정 이벤트가 발생 시 해당 함수에서 다치 호출됨.  
자바에서는 
1. Callback을 Interface로 구성하고, 호출되어질 메소드를 선언
2. 해당 Callback을 구현하는 객체 생성, 즉 호출 되어질 메소드를 구체적으로 구현
3. 다른 함수의 인자로 해당 Callback을 인자로 전달
4. 해당 함수는 특정 이벤트 발생 시 Callback에 선언된 메소드를 호출

```
kafkaProducer.send(producerRecord, (metadata, exception) -> {
            if (exception == null) {
                logger.info("\n ##### record metadata received ##### \n" +
                        "partition: " + metadata.partition() + "\n" +
                        "offset: " + metadata.offset() + "\n" +
                        "timestamp: " + metadata.timestamp());
            } else {
                logger.error("exception error from broker " + exception.getMessage());
            }
});
```


# Producer의 acks 설정에 따른 send 방식 -acks 1
- Producer는 Leader broke가 메시지 A를 정상적으로 받았는지에 대한 Ack메시지를 받은 후 다음 메시지인 메시지 B를 바로 전송. 만약 오류 메시지를 브로커로부터 받으면 메시지 A를 재 전송
- 메시지 A가 모든 Replicator에 완벽하게 복사 되었는지의 여부는 확인하지 않고 메시지 B를 전송.
- 만약 Leader가 메시지를 복제 중에 다운될 경우 다음 Leader가 될 브로커에는 메시지가 없을 수 있기 떄문에 메시지를 소실할 우려가 있음.  

Producer는 해당 Topic의 Patition의 Leader Broker에게만 메시지를 보냄

# Producer의 acks 설정에 따른 send 방식 -acks all, -1
- Producer는 Leader broke가 메시지 A를 정상적으로 받은 뒤 min.insync.replicas 개수 만큼의 Replicator에 복제를 수행한 뒤에 보내는 Ack 메시지를 받은 후 다음 메시지인 메시지 B를 바로전송, 만약 오류 메시지를 브로커로 부터 받으면 메시지 A를 재전송.
- 메시지 A가 모든 Replicator에 완벽하게 복사되었는지의 여부까지 확인후에 메시지 B를 전송.
- 메시지 손실이 되지 않도록 모든 장애 상황을 감안한 ㅓㄴ송 모드이지만 Ack를 오래 기다려야 하므로 상대적으로 전송속도가 느림.

# Producer의 Sync와 Callback Async에서의 acks와 retry
-  Callback기반의 async에서도 동일하게 acks설정에 기반하여 retry가 수행됨
- Callback기반의 async에서는 retry에 따라 Producer의 원래 메시지 전송 순서와 Broker에 기록되는 메시지 전송 순서가 변경 될 수 있음.
- Sync 방식에서 acks = 0일 경우 전송 후 ack/error를 기다리지 않음(fire and forget).

<br/><br/>

# Producer의 메시지 배치 전송의 이해
Serialize -> Partitioning -> Compression(선택) -> Record Accumulator 저장 -> Sender에서 별도의 Thread로 전송  
카프카 프로듀서는 send() 메소드를 호출할때 프로듀서 레코드가 한건 들어갑니다.  
하지만 실제 Sender Thread가 브로커에게 보낼때에는 배치단위로 보내게 됩니다.  
실제로 send 메서드는 Record Accumulator라는곳에 저장을 시키고 반환을 시켜주는 역할을 합니다.  
메시지 보내는 역할은 Sender Thread가 Record Accumulator 배치단위로 읽어와서 카프카 브로커로 보내게 됩니다.  
 
 ## Producer Record와 Record Batch
 KafkaProducer객체의 send() 메소드는 호출 시마다 하나의 ProducerRecord를 입력하지만 바로 전송되지 않고 내부 메모리(Record Accumulator)에서 단일 메시지를 토픽 파티션에 따라 Record Batch 단위로 묶인 뒤 전송됨. 메시지들은 Producer Client의 내부 메모리에 여러개의 Batch들로 buffer.memory 설정 사이즈 만큼 보관 될 수 있으며 여러개의 Batch들로 한꺼번에 전송될 수 있음  

 ## Kafka Producer Record Accumulator
 - ###  Record Accumulator는 Producer에 의해서 메시지 배치가 전송이 될 토픽과 Patition에 따라 저장되는 Kafka Producer메모리 영역
- ### Sender Thread는 Record Accumulator에 누적된 메시지 배치를 꺼내서 브로커로 전송함.
- ### KafkaProducer의 Main Thread는 send() 메소드를 호출하고  Record Accumulator에 데이터 저장하고 Sender Thread는 별개로 데이터를 브로커로 전송

 - linger.ms = Sender Thread로 메시지를 보내기전 배치로 메시지를 만들어서 보내기 위한 최대 대기 시간
 - buffer.memory = Record accumulator의 전체 메모리 사이즈
 - batch.size = 단일 배치의 사이즈

<br/><br/>

# Producer의 linger.ms와 batch.size
- Sender Thread는 기본적으로 전송할 준비가 되어 있으면 Record Accumulator에서 1개의 Batch를 가져갈수도, 여ㅑ러개의 Batch를 가져갈 수도 있음. 또한 Batch에 메시지가 다 차지 않아도 가져갈 수 있음.
- linger.ms를 0보다 크게 설정하여 Sender Thread가 하나의 Record Batch를 가져갈 때 일정 시간 대기하여 Record Batch에 메시지를 보다 많이 채울 수 있도록 적용
Sender Thread에 max.inflight.requests.per.connection = 2 이 있는데 여기서 2는 파티션에 배치를 2개 가져오라는 의미이다.  

## Producer의 linger.ms에 대한 고찰
- linger.ms를반드시0보다크게설정할필요는없음  
- Producer와 Broker간의 전송이 매우 빠르고 Producer에서 메시지를 적절한 Record Accumulator에 누적된다면 linger.ms가 0이 되어도 무방  
- 전반적인 Producer와 Broker간의 전송이 느리다면 linger.ms를 높여서 메시지가 배치로 적용될 수 있는 확률을 높이는 시도를 해볼 만함.
- linger.ms는보통20ms이하로설정권장


<br/><br/>

# Producer의 메지 전송/재 전송 시간 파라미터 이해
delivery.timeout.ms >= liger.ms + request.timeout.ms

Sender Thread가 메시지 전송을 하다 오류가나면 재 전송, 또 재 전송을 해줍니다.  
언제까지 이 일을 하느냐 바로 delivery.timeout.ms 만큼 리트라이를 하다가 종료하게 됩니다.

## max.block.ms
- Send 호출시 Record Accumulator에 입력하지 못하고 block되는 최대 시간, 초과시 Timeout Exception
## linger.ms
- Sender Thread가 Record Accumulator에서 배치별로 가져가기 위한 최대 대기시간
## request.timeout.ms
- 전송에 걸리는 최대 시간, 전송 재 시도 대기시간 제외. 초과시 retry를 하거나 Timeout Exception 발생
## retry.backoff.ms
- 전송 재 시도를 위한 대기 시간
## deliver.timeout.ms
- Producer 메시지(배치) 전송에 허용된 최대 시간, 초과 시 Timeout Exception

# Producer의 메시지 재 전송 -retries와 delivery.timeout.ms
- retries와 delivery.timeout.ms를 이용하여 재 전송 횟수 조정
- retries는 재전 송 횟수를 설정
- delivery.timeout.ms는 메시지 재전송을 멈출때까지의 시간
- 보통 retries는 무한대값으로 설정하고 delivery.timeout.ms(기본 120000, 즉 2분)를 조정하는 것을 권장  

retries 설정 횟수 만큼 재전송 시도하다가 delivery.timeout.ms가 되면 재 전송 중지
```
retries = 10
retires.backoff.ms = 30
request.timeout.ms = 10000ms
```
- retires.backoff.ms는 재 전송 주기 시간을 설정
- retries = 10, retires.backoff.ms = 30, request.timeout.ms = 10000ms인 경우 request.timeout.ms 기다린 후 재 전송하기전 30ms 이후 재전송 시도, 이와 같은 방식으로 재 전송을 10회 시도하고 더이상 retry시도하지 않음
- 만약 10회 이내에 delivery.timeout.ms에 도달하면 더 이상 retry시도하지 않음

# max.in.flight.requests.per.connection
브로커 서버의 응답없이 Producer의 sender thread가 한번에 보낼 수 있는 메시지 배치의 개수. Default 값은 5
Kafka Producer의 메시지 전송 단위는 Batch임.  
비동기 전송 시 브로커의 응답없이 한꺼번에 보낼 수 있는 Batch의 개수는 max.in.flight.requests.per.connection에 따름

# Producer 메시지 전송 순서와 Broker 메시지 저장 순서 고찰

B0가 B1보다 먼저 Producer에서 생성된 메시지 배치.  
• max.in.flight.requests.per.connection = 2 ( > 1) 에서 B0, B1 2개의 배치 메시지를 전송 시 B1은 성공적으로 기록 되었으나 B0의 경우  
Write되지 않고 Ack 전송이 되지 않는 Failure 상황이 된 경우 Producer는 BO를 재 전송하여 성공적으로 기록되며 Producer의 원래 메시지 순서와는 다르게 Broker에 저장 될 수 있음.  

# 최대 한번 전송, 적어도 한번 전송, 정확히 한번 전송
- 최대  한 번 전송(at most once)
- 적어도 한 번 전송(at least once)
- 정확히 한번 전송(exactly once)
    - 중복 없이 전송(Idempotence): Producer의 message 전송 retry시 중복제거
    - Transaction기반 전송: Consumer > Process > Producer(주로 Kafka Streams)에 주로 사용되는 Transaction기반 처리

# 중복없이 전송 (idempotence)    
- Producer는 브로커로 부터 ACK를 받은 다음에 다음 메시지 전송하되, Producer ID와 메시지 Sequence를 Header에 저장하여 전송
- 메시지 Sequence는 메시지의 고유 Sequence번호, 0부터 시작하여 순차적으로 증가 Producer ID는 Producer가 기동시마다 새롭게 생성
- 브로커에서 메시지 Sequece가 중복 될 경우 이를 메시지 로그에 기록하지 않고 Ack만 전송
- 브로커는 Producer가 보낸 메시지의 Sequence가 브로커가 가지오 이는 메시지의 Sequence보다 1만큼 큰 경우에만 브로커에 저장


Producer　　　　　　　　　　　　　　　　　　　　　Broker  
메시지 A  　　　　　　　　　　　　　　　　　　　　　　메시지A  
PID:0, SEQ:0　　--->　　1.전송　　-------->  　PID:0, SEQ:0  
PID:0, SEQ:0　　<---　　2.ACK　　<--------  　PID:0, SEQ:0  

메시지 A가 정상적으로 브로커에 기록되고 Ack전송됨(메시지 A는 프로듀서 ID:0, 메시지 시퀀스:0 으로 브로커 메모리에 저장됨, Producer는 ACK를 기다린 후 메시지 B를 전송


Producer　　　　　　　　　　　　　　　　　　　　　　　　　　　　Broker  
메시지 B  　　　　　　　　　　　　　　　　　　　　　　　　　　　 　메시지B  
PID:0, SEQ:1　　　　　---------->　　3.전송　　----------->  　PID:0, SEQ:1  
PID:0, SEQ:0　　　　　　　　　　　　　　　　　　　　　　　　　　PID:0, SEQ:0  
　　　<--x--- 4.네트워크　장애등으로 ACK나 Error 보내지 못함　<------

메시지 B가 정상적으로 Broker에 기록되었지만 네트웍 장애 등으로 Ack를 Producer에게 보내지 못함. 메시지 B는 메시지 시퀀스 1로 브로커에 저장됨   

Producer　　　　　　　　　　　　　　　　　　　　　Broker  
메시지 B  　　　　　　　　　　　　　　　　　　　　　　메시지 B  
PID:0, SEQ:1　　--->　　5. 재 전송　　-------->  　PID:0, SEQ:1  
　　　　　　　　<------　　ACK　<------

메시지 B의 Ack를 받지 못한 Producer는 메시지 B를 다시 보냄. 메시지 B는 재 전송 되었지만 브로커는 SEQ가 1인 메시지가 이 미 저장되어서 해당 메시지를 메시지 로그에 저장하지 않고 Ack만 보냄.  

<br/><br/>

# Idempotence 를 위한 Producer 설정

• enable.idempotence = true  
• acks = all  
• retries는 0 보다 큰 값  
• max.in.flight.requests.per.connection은 1에서 5사이 값  (카프카쪽에서 정함)
# Idempotence 적용 후 성능이 약간 감소(최대 20%)할 수 있지만 기본적으로 idempotence 적용을 권장 


# Idempotence 기반에서 메시지 전송 순서 유지

• B0이 가장 먼저, B1, B2 순에서 Producer에서 생성된 메시지 배치.  
• Idempotence 기반에서 max.in.flight.requests.per.connection 만큼 여러 개의 배치들이 Broker에 전송됨.  
• Broker는 메시지 배치를 처리시 write된 배치의 마지막 메시지 Sequence + 1 이 아닌 배치 메시지가 올 경우
OutOfOrderSequenceException을 생성하여 Producer에 오류로 전달.  

<br/><br/>

# Idempotence 를 위한 Producer 설정 시 유의 사항  
• Kafka 3.0 버전 부터는 Producer의 기본 설정이 Idempotence임.  
• 하지만 기본 설정중에 enable.idempotence=true를 제외하고 다른 파라미터들을 잘못설정하면(예를 들어 acks=1로 설정) Producer는 정상적으로 메시지를 보내지만 idempotence로는 동작하지 않음  
• 명시적으로  enable.idempotence=true를 설정한 뒤 다른 파라미터들을 잘못 설정하면 Config오류가 발생하면서 Producer가 기동되지 않음  

```
Properties props = new Properties();
//bootstrap.servers, key.serializer.class, value.serializer.class
props.setProperty("bootstrap.servers", "192.168.64.10:9092");
props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.64.10:9092");
props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
props.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "6");
props.setProperty(ProducerConfig.ACKS_CONFIG, "0");
props.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
```
위와 같이 설정하면 Exception in thread "main" org.apache.kafka.common.config.ConfigException: Must set acks to all in order to use the idempotent producer. Otherwise we cannot guarantee idempotence. 다음과 같은 에러로 서버가 기동되지 않습니다.  
그래서  props.setProperty(ProducerConfig.ACKS_CONFIG, "0"); 를 all 이나 -1로 설정해야 합니다.  
## 공식문서 https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html  -> enable.idempotence 키워드를 찾음