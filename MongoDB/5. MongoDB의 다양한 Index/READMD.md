# MongoDB의 Index 기본구조와 효율적인 탐색
몽고디비를 가장 많이 사용하는 이유는 유연한 스키마 구조와 빠른 속도이다. 이번 장에서는 몽고디비의 인덱스 구조와 인덱스를 효율적으로 사용하는 방법에 대해 알아보자.  
## 몽고디비의 인덱스란
필트키를 정렬된 상태로 저장된 Object이다.  
관계형 DB와 마찬가지고 비트리를 사용한다,

## 몽고디비의 인덱스 종류
- 단일필드 인덱스(Single Field Index)
- 복합필드 인덱스(Compound Index)
- 배열필드 인덱스(Multikey Index)
- 지역기반 인덱스(Geospatial Index)
- 와일드카드 인덱스(Wildcard Index)
- 텍스트 인덱스(Text Index)
- 해시 인덱스(Hash Index)

# Compound Index와 ESR Rule
## Compound Index란
여러 필드로 구성된 인덱스이다.  
인덱스의 선행필드만 사용해도 인덱스를 사용할 수 있다.
```java
db.products.createIndex( {"tiem: 1, "stock": 1, "price": 1} )

위의 인덱스를 사용할 수 있는 쿼리
db.products.find( { item: "Banana" } )
db.products.find( { item: "Banana", stock: { $gt: 5 } } )
db.products.find( { item: "Banana", stock: { $gt: 5 }, price: { $gte:10000 } } )
```

## ESR Rule
- Equality: 선행필드에 동등 연산자가 사용되어야 한다.
- E -> R(Equality Before Range) : 동등 조건 연산자가 선행필드에 사용되어야 한다.
  - db.games.createIndex( { gamertag: 1, level: 1} )
  - db.games.find( { gamertag: "Ace", level: { $gt: 50} } ) 
- E -> S(Equality Before Sort) : 동등 조건 연산자가 선행필드에 사용되어야 한다.
  - db.games.createIndex( { gamertag: 1, score: 1} )
  - db.games.find( { gamertag: "Ace"}).sort( { score: 1 } ) 
- S -> R(Sort Before Range) : 정렬 조건 연산자가 선행필드에 사용되어야 한다.
  - db.games.createIndex( { score: 1, level: 1} )
  - db.games.find( { level: { $gt: 50} } ).sort( { score: 1 } ) 
- E -> S -> R(Equality Before Sort Before Range) : 동등 조건 연산자가 선행필드에 사용되어야 한다.
  - db.games.createIndex( { gamertag: 1, score: 1, level: 1} )
  - db.games.find( { gamertag: "Ace", level: { $gt: 50} } ).sort( { score: 1 } )

## 인덱스 생성 및 사용
### 인덱스 조회
db.zips.getIndexes()  
기본적으로 _id에 대해서는 인덱스가 생성 되어 있다.  

### 인덱스 생성
1이면 오름 차순, -1이면 내림차순이다.
```java
실행 계획 확인
db.zips.find(
    {
        state: "LA",
        pop: {
            $gt: 40000
        }
    }
).sort({city:1}).explain("executionStats")

인덱스 생성
db.zips.createIndex( { state: 1 } )
db.zips.getIndexes()
ESR Rule 적용
db.zips.createIndex( { state: 1, city: 1, pop: 1 } )

db.zips.find(
    {
        state: "LA",
        pop: {
            $gt: 40000
        }
    },
    {
        _id: 0,
        state: 1,
        pop: 1,
        city: 1
    }
).sort({city:1}).explain("executionStats")
위와 같이 조회하면 인덱스의 값을 바로 사용하여서 도큐먼트를 조회하지 않는다.
```

# Multikey Index
몽고디비는 유연한 스키마를 가진다는 장점에 걸맞게 배열필드에 인덱스를 생성할 수 있다.  
배열 필드거나 배열안에 내장되어 있는 필드에 인덱스를 만들수 있고 인덱스를 생성하면 멀티키 인덱스로 구분이 된다.  
```java
{
    userID: "xyz",
    addr: [
        {zip: 10036},
        {zip: 94301}
    ]
},
{
    userID: "xyz",
    addr: [
        {zip: 10036},
        {zip: 94301}
    ]
}
```
```java
{
    userid: "abc",
    addr: 123
},
{
    userid: "abc",
    addr: 456
},
{
    userid: "abc",
    addr: 789
},
{
    userid: "abc",
    addr: 101112
}
```
첫번째는 배열을 포함한 두개의 도큐먼트이고 두번째는 4개의 도큐먼트를 가지고 있다.  
두개모두 4개의 우편번호를 표현하는데 도큐먼트 수가 다릅니다.  
addr의 인덱스를 생성하게 되면 인덱스의 수는 동일하다.  
첫번째는 멀티키 인덱스, 두번째는 싱클키 인덱스로 생성된다.  
기본적으로 Collectio Document를 저장하는 비용을 1이라고 가정하면 Index를 생성하고 삭제하는 비용은 1.5라고 한다.

### 멀티키 인덱스의 사용
```java
db.data.createIndex({sections: -1})
db.data.find({sections: 'AG1'}).explain('executionStats')

isMultiKey: true -> 멀티키 인덱스이다.
indexBounds: {sections: ["[AG1, AG1]"]} -> 인덱스의 범위를 나타낸다.

```

### 배열의 내장 도큐먼트 인덱스 생성
```java
db.grdes.createIndex({"scores.typr": 1})
db.grdes.find({"scores.type": "exam"}).explain("executionStats")

배열 복합키
db.grades.creteIndex(
        {class_id: 1, "scores.type": 1}
)
```

## Index 삭제
```java
db.grades.dropIndex({"scores.type": 1})
```

# Index의 다양한 속성
https://www.mongodb.com/docs/v5.0/indexes/#index-properties

## TTL Index
TTL(Time To Live) Index는 특정 시간이 지나면 자동으로 삭제되는 인덱스이다.  
- 싱글 필드 인덱스 기준으로만 생성가능하다.
- 날짜를 기준으로 한 필드나 날짜를 가지고 있는 배열 필드로 생성한다.
- 해당 인덱스는 백그라운드에서 실행되는 ttl 스레드에 의해 삭제된다.
```java

db.ttl.insertMany([
    {
        msg: "Hello!",
        time: new ISODate()
    },
    {
        msg: "HelloWorld!",
        time: new ISODate()
    },
])

db.ttl.find()

db.ttl.createIndex(
    {time: 1},
    {expireAfterSeconds: 60}
)
```

## Unique Index
Unique Index는 인덱스의 값이 고유해야 한다는 것을 보장하는 인덱스이다.
- _id 필드는 기본적으로 Unique Index가 생성되어 있다.
- single field index와 compound index 모두 unique index로 생성할 수 있다.
- 이미 중복된 데이터를 가지고 있는 값에 대해서는 생성할 수 없다.
- 해싱에 대해서도 부여할 수 없다.
```java
db.unique.createIndex(
    {name: 1},
    {unique: true}
)

db.unique.insertMany([
    {name: "tom"},
    {name: "john"}
])


db.unique.createIndex(
    {name: 1, age: 1},
    {unique: true}
)

db.unique.insertOne({name: "tom", age: 20}) -> 두번 실행하면 에러가 발생한다.
db.unique.insertOne({name: "tom", age: 24}) -> 나이를 변경했으므로 에러가 안남.
```

## Sparse Index
필드가 존재하는 도큐먼트만 인덱스를 생성하는 인덱스이다.
```java
db.sparse.insertOne({x: 1})
db.sparse.insertOne({x: 2})
db.sparse.insertOne({x: 3})
db.sparse.insertOne({y: 1})

db.sparse.createIndex(
    {x: 1},
    {sparse: true}
)

이렇게 생성하면 x에 대해서만 인덱스가 생성된다.

db.sparse.find()
db.sparse.find().hint({x: 1}) -> 인덱스를 사용하도록 강제한다.
```

## Partial Index
MongoDb 3.2부터 지원하는 인덱스이다. -> Sparse Index보다 권장한다.
```java
db.sparse.createIndex(
    {x: 1},
    {
        partialFilterExpression: {
            x: {$exists: true}}
    }
)

db.sparse.createIndex(
    {x: 1},
    {
        partialFilterExpression:{
            x:{$exists:true},
            x:{$gte:2}
        }
    }
)
2이상인 도큐먼트만 인덱스를 생성한다.
```

## Hidden Index
쿼리 플랜으로부터 숨겨서 해당 인덱스를 사용못하도록하는 인덱스이다.  
- 운영중인 서비스에서 특정 인덱스를 드랍하고 싶은데 사이드 이펙트가 발생할 수 있다면 hidden index를 사용한다.

```java
db.hidden.insertOne({a: 1})
db.hidden.insertOne({a: 2})

db.hidden.createIndex({a: 1}, {hidden: true})

db.hidden.find({a: 1}).explain("executionStats") -> stage: 'COLLSCAN',컬렉션 스캔을 통해 조회한다.

db.hidden.unhideIndex({a: 1}) -> MongoServerError: user is not allowed to do action [collMod] on [test.hidden]
권한 오류 에러가 난다.
1. https://cloud.mongodb.com/v2/657d852a4d915c69ef0d3679#/security/database/users
2. dataAccess -> dbAdmin으로 변경 
3. stage: 'IXSCAN'으로 인덱스 조회를 한다.
```

## Index 생성 주의 사항

4.2 버전 이전까지는 background가 기본값이 아니였다.  
background가 false이면 인덱스 생성이 완료될 때까지 다른 작업을 할 수 없다.
```java
db. collection.crateIndex(
    {"deleteDate": 1},
    {expireAfterSecods: 10},
    {background: true}        
)

db. collection.crateIndex(
    {"deleteDate": 1},
    {expireAfterSecods: 10, background: true}
)
```
ttl 인덱스를 생성하는데 2개의 파라미터를 받지만 구문 검사가 취약하여 포그라운드로 실행되어 버린다.

### Resumable Index Build
5.0부터 Index 생성중에 정상적으로 process가 종료되면 다시 기동 되었을때 기존의 progress에 이어서 index가 생성된다.  
비정상적으로 shutdown된 경우는 처음부터 index를 다시 생성한다.

### 내장된 Docyment의 Index
```java
db.users.insertOne(
    {
        name: "tom",
        address: {
            city: "seoul",
            zipcode: "12345"
        }
    }
)

1, db.users.createIndex({address: 1})
2. db.users.createIndex({"address.city": 1})
```
필터링에서 Document의 모든 필드의 순서도 같을 때만 인덱스를 사용하기 때문에 내장 Docuemnt 필드 자체에 Index를 만드는 것은 피한다.   
2번과 같이 내장 Document안의 구체적인 필드에 Index를 생성한다.
