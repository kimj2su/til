# 멀티 노드 카프카 클러스터
- 분산 시스템으로서 카프카의 성능과 가용성을 함께 향상 시킬수 있도록 구성
- 스케일 아웃 기반으로 노드증설을 통해 카프카의 메시지 전송과 읽기성능을 (거의)선형적으로 증가시킬수있음
- 데이터 복제(Replication)을 통해 분산 시스템 기반에서 카프카의 최적 가용성을 보장

## 단일 노드 구성(Scale Up)
### 처리하려는 데이터가 기하 급수적으로 늘어난다며?
- H/W을 CPU Core, Memory 용량, 디스크 용량, 네트웍 Bandwidth를 Scale Up 방식으로 증설 하기에는 한계(비용, H/W 아키텍처)
- 단일 노드에 대해서만 가용성 구성을 강화 하면 되므로 매우 안정적인 시스템 구성이 가능
- 소프트웨어에서 다양한 성능향상 기법을 도 입하기 매우 쉬움.

## 다수의 노드로 분산 구성

- 개별 H/W 노드를 Scale Out 방식으로 증 설하여 대용량 데이터 성능 처리를 선형 (?)적으로 향상
- 다수의 H/W가 1/N의 데이터를 처리하므 로이중한개의노드에서만장애가발생 해도 올바른 데이터 처리가 되지 않음.
- 다수의 H/W로 구성하였으므로 빈번한 장애 가능성, 관리의 부담
- 소프트웨어 자체에서 성능/가용성 처리 제약

## 멀티 노드 카프카 클러스터
- 분산시스템으로서카프카의성능과가용성을함께향상시킬수있도록구성
- 스케일아웃기반으로노드증설을통해카프카의메시지전송과읽기성능을(거의)선형적으로증가시킬수있음
- 데이터 복제(Replication)을 통해 분산 시스템 기반에서 카프카의 최적 가용성을 보장


## 멀티 브로커 설치하기
- 3개의 브로커를 VM에 구성
- 개별 브로커들을 각각 서로 다른 server.properties로 설정(server01.properties,
server02.properties, server03.properties)
- 개별 브로커들이 서로 다른 log.dirs를 가짐
- 개별 브로커들이 서로 다른 port 번호를 가짐(9092, 9093, 9094)
- server.properties에 서로 다른 broker.id 설정 (1, 2, 3)

```java
broker.id=1
listeners=PLAINTEXT://:9092
log.dirs=/Users/kimjisu/Desktop/dev_downloads/confluent/data/kafka-logs-01
```

### kafka_start_01.sh
```java
$CONFLUENT_HOME/bin/kafka-server-start $CONFLUENT_HOME/etc/kafka/server_01.properties

```

### zoo_start_m.sh

```java
$CONFLUENT_HOME/bin/zookeeper-server-start $CONFLUENT_HOME/etc/kafka/zookeeper_m.properties

```

### zookeeper_m.properties
```java
dataDir=/Users/kimjisu/Desktop/dev_downloads/confluent/data/zookeeper_m
```







