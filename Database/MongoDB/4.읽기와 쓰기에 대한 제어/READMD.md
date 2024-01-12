# MongoDB의 일관성 제어
- Single Document
- Transaction
- Replica Set Member
- Sharded Cluster Shard

몽고디비는 기본적으로 싱글 도큐먼트에 대해서는 원자성을 보장합니다.  
트랜잭션을 지원하지만 권장하지 않는다.  
동일한 데이터를 여러멤버에 저장하는 래플리카셋에을 통해 HA를 구성하고 멤버간의 데이터 일관성에 대한 제어를 필요로한다.  
데이터 분산이 되어 샤드간의 동일한 데이터를 갖지 않도록 제어를 필요로 한다.

# Read Preference
Read에 대한 요청을 어떤 멤버가 처리하는지 정하는 옵션이다.  
기본적으로 옵션을 주지 않으면 Write, Read는 Primary에서 처리한다.  
옵션을 통해서 Read를 Secondary에서 처리하도록 할 수 있다.  
이렇게 하면 Read에 대한 부하를 Primary에서 분산시킬 수 있다.  

## Read Preference 옵션
- primary : 기본값, Primary에서 처리
- primaryPreferred : Primary를 우선적으로 처리하고 Primary가 다운되었을 경우 Secondary에서 처리
- secondary : Secondary에서 처리
- secondaryPreferred : Secondary를 우선적으로 처리하고 Secondary가 다운되었을 경우 Primary에서 처리
- nearest : 가장 가까운 멤버에서 처리

여기서 secondary로 설정할 경우 지연된 데이터를 읽을 수 있다.  
이것에 대해 비즈니스적으로 허용가능한 부분과 허용 불가능한 경우는 다음과 같다.
1. 허용 가능한 경우 -> sns에서 업로드한 게시물이 팔로워들에게 보여지는 경우
2. 허용 불가능한 경우 -> sns에서 업로드한 게시물을 업로드한 사람이 볼 수 없는 경우

## 커넥션 스트링
```java
"mongodb+srv://jisu:<password>@cluster0.1inggwg.mongodb.net/?readPreference=secondary"
```

# Read_Write Concern1
WriteConcern은 Replica Set Member들 간의 동기화 정도를 제어하는 옵션입니다.  
쉽게 말해서 몇개의 멤버까지 복제를 완료하고 사용자에게 응답을 보낼 것인지를 결정하는 옵션입니다.  

## WriteConcern 옵션
- w : 몇개의 멤버까지 복제를 완료하고 사용자에게 응답을 보낼 것인지를 결정하는 옵션
- local: 복제를 확인하지 않고 요청한 Member의 현재 데이터를 반환
- available: local과 동일하지만, 고아 Document를 반환할 수 있다.
- majority: Member 과반수가 들고 있는 동일한 데이터를 반환
- linearizable: 쿼리 수행 전에 모든 Majority Write가 반영된 결과를 반환
- snapshor : 특정 시점에 대한 결과를 반환(Point-In-Time Query)

5버전부터는 majority가 기본값으로 과반수의 멤버의 반영을 하고 결과를 반영한다.  
몽고디비에서는 성능보다는 안전하게 데이터를 저장하는 것을 우선시한다.

# Casusal Consistency
Casusal Consistency는 시간을 기준으로 대기시키는 개념이다.

# MongoDB의 Transaction
몽고디비는 4.0버전부터 트랜잭션을 지원한다.  
몽고디비의 트랜잭션은 사용하는것을 권장하지않는다.  
몽고디비의 장점은 유연하고 다양한 형태로 구성하고 변경이 가능한 스키마인데 트랜잭션을 사용한다는 것은 컬렉션을 정규화 하겠다는 것이다.  

## 트랜잭션을 사용하는 이유
여러 작업에 대해서 하나의 논리적인 단위로 취급하여 원자성을 보장하기 위해 사용  
- 은행 송금 서비스
- 예약 시스템

몽고 디비의 4.0 버전의 트랜잭션은 같은 컬렉션의 데이터에 접근에서 변경을 일으키려고하면 TransientTransactionError가 발생한다.  
이때 retry를 통해서 해결할 수 있다.  
하지만 이런 로직을 직접 구현해야 했기 때문에 4.2버전에서는 retryableWrites 옵션을 통해서 자동으로 retry를 해준다.  
결국 retry를 내부적으로 하기 때문에 성능면에서 떨어지게 된다.
