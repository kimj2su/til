# Kafka Consumer 개요
브로커의 Topic 메시지를 읽는 역할을 수행.  
모든 Consumer들은 고유한 그룹아이디 group.id를 가지는 Consumer Group에 소속되어야 함.  
개별 Consumer Group 내에서 여러 개의 Consumer 들은 토픽 파티션 별로 분배됨.  

# Consumer의 subscribe, poll, commit 로직
Consumer는 subscribe()를 호출하여 읽어 들이려는 토픽을 등록.  
Consumer는 poll( ) 메소드를 이용하여 주기적으로 브로커의 토픽 파티션에서 메시지를 가져옴.  
메시지를 성공적으로 가져 왔으면 commit을 통해서 __consumer_offse에 다음에 읽을 offset 위치를 기재함.  

# KafkaConsumer 의 주요 수행 개요
• KafkaConsumer는 Fetcher, ConsumerClientNetwork등의 주요 내부 객체와 별도의 Heart Beat Thread를 생성  
• Fetch, ConsumerClientNetwork 객체는 Broker의 토픽 파티션에서 메시지를 Fetch 및 Poll 수행  
• Heart Beat Thread는 Consumer의 정상적인 활동을 Group Coordinator에 보고하는 역할을 수행(Group Coordinator는 주어진 시간동안 Heart Beat을 받지 못하면 Consumer들의 Rebalance를 수행 명령)

 <br/><br/>
 
# poll( ) 메소드의 동작

```
while(true) {
    //메인 스레드가 최대 1초동안 기다린다.
    ConsumerRecords<String, Integer> records = consumer.poll(Duration.ofMillis(1000)); 
    for(ConsumerRecord<String, Integer> record : records) {
        String key = record.key();
        String value = record.value();
        //poll로 가져온 데이터로 상대적으로 시간이 걸리는 작업을 수행하는 메인 Thread
    }
}
```
ConsumerRecords<K, V> consumerRecords = KafkaConsumer.poll(Duration.ofMillis(1000) )  
• 브로커나 Consumer내부 Queue에 데이터가 있다면 바로 데이터를 반환  
• 그렇지 않을 경우에는 1000ms동안 데이터 Fetch를 브로커에 계속 수행하고 결과 반환  
• Linked Queue에 데이터가 있을 경우 Fetcher는 데이터를 가져오고 반환 하며 poll() 수행 완료  
• ConsumerNetworkClient는 비동기로 계속 브로커의 메시지를 가져와서 Linked Queue에 저장  
• Linked Queue에 데이터가 없을 경우 1000ms 까지 Broker에 메시지 요청 후 poll 수행() 완료  

 <br/><br/>

# Consumer Fetcher 프로세스 개요

Fetcher는 Linked Queue 데이터를 가져오되, Linked Queue에 데이터가 없을 경우 ConsumerNetworkClient 에서 데이터를 브로커로 부터 가져올 것을 요청

fetch.min.bytes = 16384  
fetch.max.wait.ms = 500  

## fetch.min.bytes  
Fetcher가 record들을 읽어들이는 최소 bytes. 브로커는 지정된 fetch.min.bytes 이상의 새로운 메시지가 쌓일때 까 비동기I/O 지전송을하지않음. 기본은1  

## fetch.max.wait.ms  
브로커에 fetch.min.bytes 이상의 메시지가 쌓일 때까지 최대 대기 시간. 기본은 500ms

## fetch.max.bytes
Fetcher가 한번에 가져올 수 있는 최대 데이터 bytes. 기본 은 50MB  
## max.partition.fetch.bytes
Fetcher가 파티션별 한번에 최대로 가져올 수 있는 bytes 

## max.poll.records  
Fetcher가 한번에 가져올 수 있는 레코드 수. 기본은 500


 <br/><br/>

 # Wakeup을 이용하여 Consumer를 효과적으로 종료하기

 ```
기존 코드
while (true) {
    //메인 스레드가 최대 1초동안 기다린다.
    ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));
    for (ConsumerRecord record : consumerRecords) {
        logger.info("record key: {}, record value{}, partition:{}", record.key(), record.value(), record.partition());
    }
}


변경 코드
 //main thread 참조
Thread mainThread = Thread.currentThread();

//main thread 종료시 별도의 쓰레드로 카프카 컨슈머 웨이크업 메소드를 호출하게 함
Runtime.getRuntime().addShutdownHook(new Thread() {
    public void run() {
        logger.info("main program starts to exit by calling wakeup");
        kafkaConsumer.wakeup();

        try{
            mainThread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
});

try {
    while (true) {
        //메인 스레드가 최대 1초동안 기다린다.
        ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));
        for (ConsumerRecord record : consumerRecords) {
            logger.info("record key: {}, record value{}, partition:{}", record.key(), record.value(), record.partition());
        }
    }
} catch (WakeupException e) {
    logger.error("wakeup exception has been called");
} finally {
    logger.info("finally consumer is closing");
    kafkaConsumer.close();
}
```

변경 코드를 보면 현재 쓰레드를 가져와서 이 쓰레드가 종료시 훅을만든다.  
이 훅은 카프카를 닫아주는 행동을 담고 있다.


<br/><br/>

# Consumer의 auto.offset.reset

__consumer_offsets에 Consumer Group이 해당 Topic Partition 별로 offset 정보를 가지고 있지 않을 시 Consumer가 접속 시 해당  
파티션의 처음 offset 부터(earliest) 가져올 것인지, 마지막 offset 이후 부터 가져올 것인지를 설정하는 파라미터

• auto.offset.reset = earliest : 처음 offset 부터 읽음  
• auto.offset.reset = latest: 마지막 offset 부터 읽음  
• 동일 Consumer Group으로 Consumer가 새롭게 접속할 시 __consumer_offsets에 있는 offset 정보를 기반으로 메시지를 가져오기 때문에 earliest로 설정하여도 0번 오프셋 부터 읽어 들이지 않음.  
• Consumer Group의 Consumer가 모두 종료 되어도 Consumer Group이 읽어들인 offset 정보는 7일동안 __consumer_offsets에 저장되어 있음 (offsets.retention.minutes)  
• 해당 Topic이 삭제되고 재 생성될 경우에는 해당 topic에 대한 Consumer Group의 offset 정보는 0으로 __consumer_offsets으로 기록됨  

<br/><br/>

# Broker의 Group Coordinator와 Consumer/Consumer Group

