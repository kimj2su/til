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