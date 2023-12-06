# SQL vs NoSQL
## SQL
- 관계형 데이터베이스
- 테이블 형태의 데이터베이스
- 데이터의 중복을 최소화하고, 데이터의 무결성을 보장하기 위해 정규화를 사용
- 하나의 레코드를 확인하기 위해 여러 테이블을 Join하여 가시성이 떨어진다.
- 스키마가 엄격해서 변경에 대한 공수가 크다.

## NoSQL
- 비관계형 데이터베이스
- 테이블 형태가 아닌 도큐먼트, Key-Value 형태의 데이터베이스
- 도큐먼트란 json 형태로 표현한다.
- 데이터 접근성과 가시성이 좋다.
- 스키마가 유연해서 변경에 대한 공수가 적다.
- Join 없이 조회가 가능해서 응답 속도가 일반적으로 빠르다.
- 데이터의 일관성을 보장하지 않는다.
- 데이터의 중복을 허용한다.
- 스키마가 자유롭지만, 스키마 설계를 잘해야 성능 저하를 피할 수 있다.
- HA와 Sharding에 대한 솔류선을 자체적으로 지원하고 있어 Scale-Out이 간편하다.
- 확장 시, Application의 변경사항이 없다.

# MongoDB의 구조
Database -> Collection -> Document -> Field 순으로 구조가 형성되어 있다.
admin, local, config는 몽고디비를 관리하는데 사용된다.
Collection은 동적 스키마를 갖는다
Collection
- RDBMS의 Table과 유사한 개념
- Document: RDBMS의 Row와 유사한 개념
- Collection 단위로 Index를 생성할 수 있다.
- Collection 단위로 Shard를 나눌 수 있다.

Document
- JSON 형태로 표현하고 BSON 형태로 저장한다.
- 모든 Document는 고유한 _id 필드를 가진다. 생성하면 ObjectId 타입의 고유한 값을 저장한다.
- 생성 시, 상위 구조인 Databases나 Collection이 없다면 먼저 생성하고 Document를 생성한다.
- Document의 최대크기는 16mb이다.

# MongoDB 배포 형태
3가지의 형태가 있다. 
- Standalone
- Replica Set
- Sharding

## Standalone
- 단일 인스턴스로 구성된 배포 형태
- Study, Test 용도로 사용한다.

## Replica Set
- 여러 인스턴스로 구성된 배포 형태
- Master-Slave 구조로 구성된다.
- Master가 죽으면 Slave 중 하나가 Master가 되어 Failover를 수행한다.
- HA(High Availability)를 보장한다.
- 현업에서 가장 많이 사용하는 배포 형태이다.

## Sharding
- 여러 인스턴스로 구성된 배포 형태
- 데이터를 분산 저장하고, 조회할 때 분산된 데이터를 모아서 조회한다.
- Scale-Out을 위해 사용한다.
- Replica Set과 함께 사용한다.
- Replica Set과 마찬가지로 Master-Slave 구조로 구성된다.
- Shard 안에 Replica Set을 구성한다.

## Replica Set

Primary 
- Read/Write 요청 모두 처리할 수 있다.
- Write를 처리하는 유일한멤버이다.
- Replica Set에는 하나의 Primary만 존재한다.

Secondary
- Read 요청만 처리할 수 있다.
- 복제를 통한 Primary와 동일한 데이터 셋을 유지한다.
- Replica Set에 여러개가 존재할 수 있다.

## Sharded Cluster
- Shard
    - 데이터를 분산 저장한다.
    - Replica Set으로 구성한다.
    - Shard Key를 기준으로 데이터를 분산 저장한다.
    - Shard Key는 반드시 Index로 생성되어야 한다.
    - Shard Key는 변경할 수 없다.
    - Shard Key는 데이터의 분산을 결정한다.
    - Shard Key는 데이터의 조회 성능에 영향을 미친다.
    - Shard Key는 데이터의 분산을 결정한다.
    - Shard Key는 데이터의 조회 성능에 영향을 미친다.

- 장점
  - 용량의 한계를 극복할 수 있다.
  - 데이터 규모와 부하가 크더라도 처리량이 좋다.
  - 고가용성을 보장한다.
  - 하드웨어에 대한 제약을 해결할 수 있다.
- 단점
  - 관리가 비교적 복잡하다.
  - Replica Set보다 성능이 떨어진다. -> 샤드를 찾아 쿼리를 해야하기 때문에

## Shrding vs Partitioning
Sharding
- 하나의 큰 데이터를 여러 서브셋으로 나누어 여러 인스턴스에 저장하는 기술

Partitioning
- 하나의 큰 데이터를 여러 서브넷으로 나누어 하나의 인스턴스의 여러 테이블로 나누어 저장하는 기술

## Replica Set vs Sharded Cluster
Replica Set
- 각각 멤버가 같은 데이터 셋을 갖는다.

Sharded Cluster
- 각각 Shard가 다른 데이터 서브셋을 갖는다.

## 샤딩 전략

### Range Sharding
- Shard Key의 값의 범위를 기준으로 데이터를 분산 저장한다.
- 데이터의 분산이 고르지 않을 수 있다. (불균형) -> 
  - shard A : minKey <= x < 10
  - shard B : 10 <= x < 20
  - Shard C : 20 <= x < maxKey

### Hash Sharding
- Shard Key의 해시값을 기준으로 데이터를 분산 저장한다.
- 데이터의 분산이 고르다. (균형)
- Shard Key의 값이 순차적이지 않다면, 데이터의 조회 성능이 떨어진다.

### Zone Sharding
- Shard Key의 값의 범위를 기준으로 데이터를 분산 저장한다.
- range와 hash의 장점을 결합한 전략이다.
- 값에 대한 범위를 지정하여 데이터를 분산 저장한다.
- zones [A] X : 1-10, [B] X : 11-20, [C] X : 21-30
- Shard Alpha : zone : ["A"], Shard Beta : zone : ["B"], Shard Charlie : zone : ["C"]
- 글로벌로 지역별로 데이터를 분산 시켜야 하는 경우 ip를 기준으로  한국에 살고 있으면 한국에 있는 샤드 쪽으로 요청을 보내는 방식.

## Replica Set vs Sharded Cluster 어떻게 배포해야할까
가능하면 Replica Set으로 배포하고, 데이터의 양이 많아지면 Sharded Cluster로 배포한다.

## Storage Engine
- 데이터가 메모리와 디스크에 어떻게 저장하고 읽을지 관리하는 컴포넌트이다.
- MySQL과 동일하게 Plugin 형태로 되어 있어 몽고디비도 다양한 스토리지엔진을 사용할 수 있다.
- 몽고디비3.2부터 기본 스토리지엔진은 wiredTiger이다.(기존에는 MMaPv1 사용)
- WriedTiger가 도입되면서 몽고디비의 성능은 큰 폭으로 좋아졌다.

### WiredTiger
Data Compression 지원
Document Level Locking 지원

### MMaPv1
Data Compression 미지원
버전에 따란 Database 혹은 Collection 레벨의 Lock