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