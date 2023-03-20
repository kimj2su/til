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


