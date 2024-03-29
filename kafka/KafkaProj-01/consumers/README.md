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
Consumer Group내에 새로운 Consumer가 추가되거나 기존 Consumer가 종료 될 때, 또는 Topic에 새로운 Partition이 추가될 때 Broker의 Group Coordinator는 Consumer Group내의 Consumer들에게 파티션을 재 할당하는 Rebalancing을 수행하도록 지시

## Group Coordinator
• Consumer들의 Join Group 정보, Partition 매핑 정보 관리  
• Consumer 들의 HeartBeat 관리

1. Consumer Group내의 Consumer가 브로커에 최초 접속 요청 시 Group Coordinator 생성.
2. 동일 group.id로 여러 개의 Consumer로 Broker의 Group Coordinator로 접속
3. 가장 빨리 Group에 Join 요청을 한 Consumer에게 Consumer Group내의 Leader Consumer로 지정
4. Leader로 지정된 Consumer는 파티션 할당전략에 따라 Consumer들에게 파티션 할당
5. Leader Consumer는 최종 할당된 파티션 정보를 Group Coordinator에게 전달.
6. 정보 전달 성공을 공유한 뒤 개별 Consumer들은 할당된 파티션에서 메시지 읽음

<br/><br/>

# Consumer 스태틱 그룹 멤머쉽의 필요성
• 많은 Consumer를 가지는 Consumer Group에서 Rebalance가 발생하면 모든 Consumer들이 Rebalance 를 수행하므로 많은 시간이 소모 되고 대량 데이터 처리시 Lag가 더 길어질 수 있음  
• 유지보수 차원의 Consumer Restart도 Rebalance를 초래하므로 불필요한 Rebalance를 발생 시키지 않 을방법대두  
• Consumer Group내의 Consumer들에게 고정된 id를 부여.  
• Consumer 별로 Consumer Group 최초 조인 시 할당된 파티션을 그대로 유지하고 Consumer가 shutdown되어도 session.timeout.ms내에 재 기동되면 rebalance가 수행되지 않고, 기존 파티션이 재 할당됨.  
• Consumer #3 가 종료 되었지만 Rebalance가 일어나지 않으며, Partition #3는
다른 Consumer에 재 할당되지 않고 읽혀지지 않음  
• Consumer #3가 session.timeout.ms 내에 다시 기동되면 Partition #3는
Consumer #3에 할당  
• Consumer #3가 session.timeout.ms 내에 기동되지 않으면 Rebalance가 수행
된 후 Partition #3가 다른 Consumer에 할당됨.  
• 스태틱 그룹 멤버쉽을 적용할 경우 session.timeout.ms 를 좀 더 큰 값으로 설정  

```
[2023-04-21 00:59:20,240] INFO [GroupCoordinator 0]: Static member with groupInstanceId=3 and unknown member id joins group group-01-static in Stable state. Replacing previously mapped member 3-f10cb99a-b96c-4b45-9e7e-25ac21762b14 with this groupInstanceId. (kafka.coordinator.group.GroupCoordinator)
[2023-04-21 00:59:20,243] INFO [GroupCoordinator 0]: Static member which joins during Stable stage and doesn't affect selectProtocol will not trigger rebalance. (kafka.coordinator.group.GroupCoordinator)
```
컨슈머 기동시 session.timeout.ms = 45000 45초가 되어있는데 이 안에 재 기동시 리밸런싱이 안일어난다.

<br/><br/>

# KafkaConsumer의 Heart Beat Thread

KafkaConsumer  
Fetcher  
ConsumerNetworkClient  
SubscriptionState  
ConsumerCoordinator  
Heart Beat Thread  
Heart Beat Thread 를 통해서 브로커의 Group Coordinator에 Consumer의 상태를 전송

<br/><br/>

## 주요 파라미터

| Consumer 파라미터명      | 기본값(ms)               | 설명 |
|-----------------------|----------------------|--------------------------|
| heartbeat.interval.ms | 3000   | Heart Beat Thread가 Heart Beat을 보내는 간격. <br>session.timeout.ms 보다 낮게 설정되어야 함. Session.timeout.ms의 1/3 보다 낮게 설정 권장|
| session.timeout.ms    | 45000  | 브로커가 Consumer로 Heart Beat을 기다리는 최대 시간. 브로커는 이 시간동 안 Heart beat을 consumer로 부터 받지 못하면 해당 consumer를 Group에서 제 외하도록 rebalancing 명령을 지시  
| max.poll.interval.ms  | 3000000|이전 poll( )호출 후 다음 호출 poll( )까지 브로커가 기다리는 시간. 해당 시간동 안 poll( )호출이 Consumer로 부터 이뤄지지 않으면 해당 consumer는 문제가 있 는 것으로 판단하고 브로커는 rebalance 명령을 보냄.

<br/><br/>

# Consumer에 Rebalance가 발생하는 상황
Consumer Group내에 새로운 Consumer가 추가되거나 기존 Consumer가 종료 될 때, 또는 Topic에 새로운 Partition이 추가될 때  
• session.timeout.ms 이내에 Heartbeat이 응답이 없거나, max.poll.interval.ms 이내에 poll( ) 메소드가 호출되지 않을 경우  

<br/><br/>

# Consumer에서 여러개의 Topic 읽기
```
kafka-topics --bootstrap-server localhost:9092 --create --topic topic-p3-t1 --partitions 3
Created topic topic-p3-t1.  

kafka-topics --bootstrap-server localhost:9092 --create --topic topic-p3-t2 --partitions 3
Created topic topic-p3-t2.  

kafka-console-producer --bootstrap-server localhost:9092 --topic topic-p3-t1
kafka-console-producer --bootstrap-server localhost:9092 --topic topic-p3-t2

메세지 전송을 한다.  
KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
kafkaConsumer.subscribe(List.of("topic-p3-t1", "topic-p3-t2"));

컨슈머에서 리스트로 토픽 이름을 받으면 여러개의 토픽을 읽을 수 있다.
```

<br/><br/>

# Consumer Rebalancing Protocol – Eager 모드

• Rebalance 수행 시 기존 Consumer들의 모든 파티션 할당을 취소하고 잠시 메시지를 읽지 않음. 이후 새롭게 Consumer에 파티션 을 다시 할당 받고 다시 메시지를 읽음.  
• 모든 Consumer가 잠시 메시지를 읽지 않는 시간으로 인해 Lag가 상대적으로 크게 발생할 가능성 있음.  

파티션 할당 전략(partition.assignment.stragegy)중 Range, Round Robin, Sticky 방식이 여기에 해당  

<br/><br/> 

# Consumer Rebalancing Protocol – (Incremental) Cooperative모드
• Rebalance 수행 시 기존 Consumer들의 모든 파티션 할당을 취소하지 않고 대상이 되는 Consumer들에 대해서 파티션에 따라 점 진적으로(Incremental) Consumer를 할당하면서 Rebalance를 수행.  
• 전체 Consumer가 메시지 읽기를 중지하지 않으며 개별 Consumer가 협력적으로(Cooperative) 영향을 받는 파티션만 Rebalance 로 재 분배. 많은 Consumer를 가지는 Consumer Group내에서 Rebalance 시간이 오래 걸릴 시 활용도 높음

<br/><br/> 

파티션 할당 전략(partition.assignment.stragegy)중 Cooperative Sticky에 해당

# Consumer 파티션 할당 전략 목표
• Consumer의 부하를 파티션 별로 균등하게 할당  
• 데이터 처리 및 리밸런싱의 효율성 극대화

<br/><br/>

# Consumer 파티션 할당 전략 유형

| 파티션 할당 전략     |  내용            |   
|-----------------------|----------------------|
| Range 할당 전략 | • 서로 다른 2개 이상의 토픽을 Consumer들이 Subscription 할 시 토픽별 동일한 파티션을 특정 Consumer에게 할당하는 전략. <br/> • 여러 토픽들에서 동일한 키값으로 되어 있는 파티션은 특정 Consumer에 할당하여 해당 Consumer 가여러토픽의동일키값으로데이터처리를용이하게할수있도록지원
|
| Round Robin 할당 전략    | • 파티션별로 Consumer들이 균등하게 부하를 분배할 수 있도록 여러 토픽들의 파티션들을 Consumer들에게 순차적인 Round robin 방식으로 할당
| Sticky 할당 전략  | • 최초에 할당된 파티션과 Consumer 매핑을 Rebalance 수행되어도 가급적 그대로 유지 할 수 있도록 지원하는 전략. <br/> • 하지만 Eager Protocol 기반이므로 Rebalance 시 모든 Consumer의 파티션 매핑이 해제 된 후에 다시 매핑되는 형태임.
| Cooperative(협력적) Sticky 할당 전략 | • 최초에 할당된 파티션과 Consumer 매핑을 Rebalance수행되어도 가급적 그대로 유지 할 수 있도록 지원함과 동시에 Cooperative Protocol 기반으로 Rebalance 시 모든 Consumer의 파티션 매핑이 해제 되지 않고 Rebalance 연관된 파티션과 Consumer만 재 매핑됨



<br/><br/>
# Consummer 파티션 하당 전략 실습 Rangedhk Round Robin 방식 할당 실습
ConsumerMTopicRebalance 컨슈머를 실행시키면
```
partition.assignment.strategy = [class org.apache.kafka.clients.consumer.RangeAssignor, class org.apache.kafka.clients.consumer.CooperativeStickyAssignor]
``` 
다음과 같이 레인지가 보입니다.

```
kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group group-assign

GROUP           TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID                                                  HOST            CLIENT-ID
group-assign    topic-p3-t2     1          3               3               0               consumer-group-assign-1-de7062a0-798c-4cb2-b54a-dc81c85461f6 /127.0.0.1      consumer-group-assign-1
group-assign    topic-p3-t1     1          1               1               0               consumer-group-assign-1-de7062a0-798c-4cb2-b54a-dc81c85461f6 /127.0.0.1      consumer-group-assign-1
group-assign    topic-p3-t2     2          4               4               0               consumer-group-assign-1-de7062a0-798c-4cb2-b54a-dc81c85461f6 /127.0.0.1      consumer-group-assign-1
group-assign    topic-p3-t2     0          0               0               0               consumer-group-assign-1-de7062a0-798c-4cb2-b54a-dc81c85461f6 /127.0.0.1      consumer-group-assign-1
group-assign    topic-p3-t1     2          2               2               0               consumer-group-assign-1-de7062a0-798c-4cb2-b54a-dc81c85461f6 /127.0.0.1      consumer-group-assign-1
group-assign    topic-p3-t1     0          4               4               0               consumer-group-assign-1-de7062a0-798c-4cb2-b54a-dc81c85461f6 /127.0.0.1      consumer-group-assign-1
```

여기서 하나의 컨슈머를 더 기동해보겠습니다.
```
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-group-assign-1, groupId=group-assign] Adding newly assigned partitions: topic-p3-t2-1, topic-p3-t1-0, topic-p3-t2-0, topic-p3-t1-1
```

여기서 2개가 나올수도 있고 4개가 나올수도있다.

## 기존 컨슈머에서 리보크로 6개의 토픽을 포기한뒤 2개를 할당받는 모습이다.
```
Request joining group due to: group is already rebalancing
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-group-assign-1, groupId=group-assign] Revoke previously assigned partitions topic-p3-t2-1, topic-p3-t1-0, topic-p3-t2-0, topic-p3-t1-2, topic-p3-t2-2, topic-p3-t1-1
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-group-assign-1, groupId=group-assign] (Re-)joining group
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-group-assign-1, groupId=group-assign] Successfully joined group with generation Generation{generationId=2, memberId='consumer-group-assign-1-de7062a0-798c-4cb2-b54a-dc81c85461f6', protocol='range'}
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-group-assign-1, groupId=group-assign] Finished assignment for group at generation 2: {consumer-group-assign-1-de7062a0-798c-4cb2-b54a-dc81c85461f6=Assignment(partitions=[topic-p3-t2-2, topic-p3-t1-2]), consumer-group-assign-1-b0d1a555-3b46-4607-a85e-3c725f1999de=Assignment(partitions=[topic-p3-t2-0, topic-p3-t2-1, topic-p3-t1-0, topic-p3-t1-1])}
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-group-assign-1, groupId=group-assign] Successfully synced group in generation Generation{generationId=2, memberId='consumer-group-assign-1-de7062a0-798c-4cb2-b54a-dc81c85461f6', protocol='range'}
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-group-assign-1, groupId=group-assign] Notifying assignor about the new Assignment(partitions=[topic-p3-t2-2, topic-p3-t1-2])
[main] INFO org.apache.kafka.clients.consumer.internals.ConsumerCoordinator - [Consumer clientId=consumer-group-assign-1, groupId=group-assign] Adding newly assigned partitions: topic-p3-t1-2, topic-p3-t2-2
```



# Round Robin으로 바꾸기

```
props.setProperty(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());
```

# Cooperative Sticky으로 바꾸기

```
props.setProperty(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, CooperativeStickyAssignor.class.getName());


출력 값
partition.assignment.strategy = [org.apache.kafka.clients.consumer.CooperativeStickyAssignor]


이런식으로 포기하는 토픽이 바로 바로 출력되고 할당되는 것을 보여준다.
Assigned partitions:        [topic-p3-t1-0, topic-p3-t1-1]
Current owned partitions:   [topic-p3-t1-0, topic-p3-t1-2,topic-p3-t1-1]
Added partitions (assigned - owned):       []
Revoked partitions (owned - assigned):     [topic-p3-t1-2]
```





