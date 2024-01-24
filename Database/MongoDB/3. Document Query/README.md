# Document Query
RDBMS에서는 SQL을 쓰지만
몽고 디비에서는 MQL(MongoDB Query Language)을 쓴다.
https://www.mongodb.com/docs/manual/reference/sql-comparison/

## Query Filter 와 Operator
https://www.mongodb.com/docs/manual/crud/#read-operations
### Query Filter
CRUD 작업중 첫번째 인자를 쿼리필터라한다.

### Operator
https://www.mongodb.com/docs/manual/reference/operator/
쿼리필터에 사용되는 연산자들이다.
$lt, $gt 등 다양한 연산자가 있다.

## CRUD
### Create
단건
```java
db.employees.insertOne({
        name: "jisu",
        age: "27",
        dept : "Database",
        joinDate: new ISODate("2023-12-01"),
        salary: 400000,
        bonus: null
})
```

여러건
```java
db.employees.insertMany([
        {
                name: "jisu2",
                age: "45",
                dept : "Network",
                joinDate: new ISODate("1999-11-15"),
                salary: 100000,
                resignationDate: new ISODate("2020-12-01"),
                bonus: null
        },
        {
                name: "jisu3",
                age: "34",
                dept : "DevOps",
                isNegotiating: true
        }
])
```

성능 측정
몽고 쉘은 자바스크립트로 되어 있어서 자바 스크립트를 사용 가능하다.  
insertOne 300개 -> 5초 정도 걸림
insertMany 300개 -> 0.5초 정도 걸림
```java
for (i = 0; i < 300; i++) {
        db.employees.insertOne({
                a : i
        })
}

var docs = [];
for (i = 0; i < 300; i++) {
    docs.push({a : i})
}
db.employees.insertOne(docs)
```

## Update
단건 업데이트
```java
db.employees.updateOne(
        {name: "jisu3"},
        {
            $set: {
                salary: 500000,
                dept: "Database",
                joinDate: new ISODate("2023-12-01")
            },
            $unset: {
                isNegotiating: ""
            }
        }
)
```
name : Query Filter -> 조건
$set : Update Operator -> 수정할 내용
$unset : Update Operator -> 삭제할 내용  

여러건 업데이트
```java
db.employees.updateMany(
        {resignationDate: {$exists: false}, joinDate: {$exists: true}},
        {
            $mul: {salary: Decimal128("1.1")}
        }
)
```
퇴사일자가 없고, 입사일자가 있는 사원의 연봉을 10% 인상시킨다.

null 처리
```java
db.employees.updateMany(
        {resignationDate: {$exists: false}, bonus: null},
        {
            $set: {bonus: 100000}
        }
)
```
위와 같은 쿼리에서 bonus를 null인 쿼리 필터를 사용하면, bonus가 없는 사원들도 포함되어 버린다.
그래서 $exists를 사용한다.
```java
db.employees.updateMany(
        {resignationDate: {$exists: false}, bonus: {$exists: false}},
        {
            $set: {bonus: 100000}
        }
)
```

## Delete
```java
db.employees.deleteOne({name: "jisu"})
db.employees.deleteMany({})
```
위와 같이 쿼리필터를 넣어서 삭제할 수 있다.  
쿼리 필터 조건을 넣지 않으면 모든 데이터를 삭제한다.  
하지만 컬렉션은 남아있다.
```java
show collections
db.employees.drop()
```

## Read
db 조회
```java
show dbs
```
db 사용
```java
use test
```
컬렉션 조회
```java
show collections
```
컬렉션 조회
```java
db.planets.find()
```
단건 조회
```java
db.planets.find({name: "Mars"})
```
조건 조회
```java
db.planets.find({
        hasRings: true, 
        orderFromSun: {$lte: 6}
})

db.planets.find({
        $and: [
                {hasRings: true},
                {orderFromSun: {$lte: 6}}
        ]
})

db.planets.find({
        $or: [
            { hasRings:{ $ne:false } },
            { orderFromSun: { $gt: 6 } }
        ]
})
```
쿼리 필터에서 ,는 AND 조건이다.
$and, $or를 사용해서 AND, OR 조건을 사용할 수 있다.
$ne는 Not Equal의 약자이다.
$lte는 Less Than Equal의 약자이다. -> 6보다 작은.
$gt는 Greater Than의 약자이다. -> 6보다 큰.

배열 조회
```java
db.planets.find(
        { mainAtmosphere: {$in: ['O2']} }
)
```
mainAtmosphere에 O2가 포함된 데이터를 조회한다.

첫번쨰 인자에는 쿼리 필터가 들어가고 오퍼레이터를 사용할때는 $표시로 도큐먼트 조건들을 비교한다.

## 유요한 Query 함수들
https://mongodb.com/docs/v5.0/reference/method/js-collection/

### bulkWrite : 벌크성 작업을 사용할 때 사용
```java
db.bulk.bulkWrite(
    [
        {insertOne: {doc: 1, order: 1}},
        {insertOne: {doc: 2, order: 2}},
        {insertOne: {doc: 3, order: 3}},
        {insertOne: {doc: 4, order: 4}},
        {insertOne: {doc: 5, order: 5}},
        {
            deleteOne: {
                filter: {doc: 1}
            }
        },
        {
            updateOne: {
                filter: {doc: 2},
                update: {$set: {order: 10}}
            }
        },
        {ordered: false}
    ]
)
```
ordered: false를 사용하면 순서를 보장하지 않는다. -> 성능 좋은대로 실행함
몽고 디비는 도큐먼트 레벨에서 원자성을 보장한다.  
insertMany 함수를 사용해서 100만개를 데이터를 넣다가 중간에 문제가 생기면 중간에 삽입한곳에서 끝나 버린다. -> 롤백하지 않음.  
몽고 디비는 트랜잭션을 지원하지만 권장하지 않는다.  

### countDocument, estimatedDocumentCount
```java
db.bulk.countDocuments({dept: "Database"})
db.bulk.estimatedDocumentCount()
```
countDocument는 실제 실행을 하고, estimatedDocumentCount는 예상되는 값을 반환한다. 성능 더 좋음

### distinct
```java
db.bulk.distinct("doc")
```

### findAndModify
동시성에 대한 제어를 위한 함수이다.  
수정하기 전에 도큐먼트를 반환하고 수정하거나 수정하고 수정된 값을 반환해주는 함수이다.  
하나씩만 수정할 수 있다.  

```java
db.bulk.findAndModify({
    query: {doc: 5},
    sort: {order: -1},
    update: {$inc: {doc: 1}},
})
```
query : 쿼리 필터  
sort : 정렬 -1 : 내림차순, 1 : 오름차순  
몽고디비는 auto increment를 지원하지 않는다.  
sequence 컬렉션을 만들어 사용한다.  
```java
db.sequence.insertOne({_seq: 0})
db.sequence.findAndModify({
    query: {},
    sort: {_seq: -1},
    update: {$inc: {_seq: 1}},
})
```
그래서 동시성에 대비하여 한번 실행후 시퀀스 값을 반환 받아 사용한다.  

### getIndexes
인덱스 조회
```java
db.bulk.getIndexes()
db.bulk.createIndex({doc: 1})
```

### replaceOne
도큐먼트 전체를 바꾸는데 사용한다.  
_id는 바꿀 수 없다.  -> 한번 정의 되면 수정될 수 없다.
```java
db.bulk.replaceOne(
    {doc: 1},
    {doc: 13}
)
```

## 배열과 내장 도큐먼트 다루는 방법(Read)

### 내장 도큐먼트
```java
db.sales.findOne({
    customer: {
        gender: "F",
        age: 39,
        email: "beecho@wic.be", 
        satisfaction: 3 
    }
})
```
위와 같이 배열을 사용하면 배열의 순서가 중요하다.

```java
db.sales.findOne({
    "customer.email": "beecho@wic.be"
})
db.sales.findOne({
    "customer.age": {$lt: 20}
})
```
위와 같이 도큐먼트의 키를 사용해서 조회할 수 있다.

### 배열(READ)
```java
db.inventory.insertMany([
   { item: "journal", qty: 25, tags: ["blank", "red"], dim_cm: [ 14, 21 ] },
   { item: "notebook", qty: 50, tags: ["red", "blank"], dim_cm: [ 14, 21 ] },
   { item: "paper", qty: 100, tags: ["red", "blank", "plain"], dim_cm: [ 14, 21 ] },
   { item: "planner", qty: 75, tags: ["blank", "red"], dim_cm: [ 22.85, 30 ] },
   { item: "postcard", qty: 45, tags: ["blue"], dim_cm: [ 10, 15.25 ] },
   { item: "postcard", qty: 45, tags: ["blue"], dim_cm: [ 10, 13.14 ] }
])

두개의 값이 있는 도큐먼트만 나온다.
db.inventory.find({
        tags: ["red", "blank"]
})

red,blank 모두 포함된 도큐먼트가 나온다.
db.inventory.find({
        tags: {$all: ["red", "blank"]}
})

red,blank가 하나라도 포함된 도큐먼트가 나온다.
db.inventory.find({
        tags: {$in: ["red", "blank"]}
})

blank가 포함된 도큐먼트가 나온다.
db.inventory.find({
        tags: "blank"
})
        
dim_cm이 15보다 큰 도큐먼트가 나온다.
db.inventory.find({
        dim_cm: {$gt: 15}
})
        
dim_cm이 15보다 크고 20보다 작은 도큐먼트가 나온다.
필드에 직접 쿼리 비교를하면  배열 요소 각각이 조건에 맞으면 나온다.
db.inventory.find({
    dim_cm: {$gt: 15, $lt: 20}
})
        
dim_cm이 15보다 크고 20보다 작은 도큐먼트가 나온다.
elemMatch를 사용하면 하나라도 만족하면 나온다. -> 더 정확함
db.inventory.find({
    dim_cm: {$elemMatch: {$gt: 15, $lt: 20}}
})

특정 위치 조회 -> 인덱스 번호
db.inventory.find({
    "dim_cm.1": {$lt: 20}
})

사이즈 조회
db.inventory.find({
    tags: {$size: 3}
})

db.sales.find({
    "items.name": "binder",
    "items.quantity": {$lte: 6}
})

db.sales.find({
    items: {
        $elemMatch: {
            name: "binder",
            quantity: {$lte: 6}
        }
    }
})

프로젝션
db.sales.find(
{
    items: {
        $elemMatch: {
            name: "binder",
            quantity: {$lte: 6}
        }
    }
},
{
    saleDate: 1,
    "items.$": 1,
    storeLocation: 1,
    customer: 1
}
)
```

### 배열(UPDATE)
```java
db.students.insertMany([
{
        _id: 1,
        grades: [85, 80, 80]
    },
    {
        _id: 2,
        grades: [88, 90, 92]
    },
    {
        _id: 3,
        grades: [85, 100, 90]
    }
])

조건에 맞는 단건 업데이트
db.students.updateOne(
    {_id: 1, grades: 80},
    {$set: {"grades.$": 82}}
)

조건에 맞는 전체 업데이트
db.students.updateMany(
    {},
    {$inc: {"grades.$[]": 10}}
)

db.students.insertMany([
    {
        _id: 4,
        grades: [
            {grade:80, mean:75, std:8},
            {grade:85, mean:90, std:5},
            {grade:80, mean:85, std:8},
        ]
    }
])

포지셔닝오퍼레이터
db.students.updateOne(
    {_id: 4, "grades.grade": 85},
    {$set: {"grades.$.std": 6}}
)

grades의 모든 grade를 100으로 변경
db.students.updateOne(
    { _id: 4, grades: {$elemMatch: {grade: {$gte: 85}}} },
    {$set: {"grades.$[].grade": 100}}
)

조건에 맞는 배열만 변경
db.students.insertMany([
    {
        _id: 6,
        grades: [
            {grade:90, mean:75, std:8},
            {grade:87, mean:90, std:6},
            {grade:85, mean:85, std:8},
        ]
    }
])

db.students.updateMany(
    { _id: 6},
    { $set: { "grades.$[element].grade": 100 } },
    { arrayFilters: [{ "element.grade": {$gte: 87} }]}
)

db.students.insertOne(
    {
        _id: 7,
        grades: [
            { type: "quiz", questions: [ 10, 8, 5 ]},
            { type: "quiz", questions: [ 8, 9, 6 ]},
            { type: "hw", questions: [ 5, 4, 3 ]},
            { type: "exam", questions: [ 25, 10, 23, 0 ]},
        ]
    }
)

db.students.updateOne(
    { _id: 7 },
    { $inc: { "grades.$[].questions.$[score]": 2 } },
    {arrayFilters: [{score: {$gte: 8}}] }
)

db.shoping.insertMany(
    [
        {
            _id: 1,
            cart: ['banana', 'cheeze', 'milk'],
            coupons: ['10%', '20%', '30%']
        },
        {
            _id : 2,
            cart: [],
            coupons: []
        }
    ]
)

addToSet : 중복을 허용하지 않는다.

db.shoping.updateOne(
    { _id: 1 },
    { $addToSet: { cart: 'beer'}}
)

[beer, candy] 배열 자체가 하나의 요소로 들어간다.
db.shoping.updateOne(
    { _id: 1 },
    { $addToSet: { cart: ['beer', 'candy']}}
)

각각 넣어주기 위해 $each를 사용한다.
db.shoping.updateOne(
    { _id: 1 },
    { $addToSet: { cart: { $each: ['beer', 'candy'] } } }
)

$pull : 값을 기준으로 배열에서 특정 요소를 삭제한다.
db.shoping.updateOne(
    { _id: 1 },
    { $pull: { cart: 'beer' } } 
)

$in : 배열에서 특정 요소를 삭제한다.
db.shoping.updateOne(
    { _id: 1 },
    { $pull: { cart: { $in: [['beer', 'candy'], 'milk'] } } } 
)

$pop : 배열의 첫번째 요소를 삭제한다. 음수는 가장 첫번째 값을 삭제한다. 양수는 가장 마지막 값을 삭제한다.
db.shoping.updateOne(
    { _id: 1 },
    { $pop: { cart: 1, coupons: -1 }}
)

$push : 배열의 끝에 요소를 추가한다.
db.shoping.updateOne(
    { _id: 1 },
    { $push: { cart: 'popcorn' }}
)

db.shoping.updateOne(
    { _id: 1 },
    { $push: { coupons: { $each: ['25%', '35%' ] } } }
)

$position : 특정 위치에 요소를 추가한다. 음수면 뒤에서부터 시작한다.
db.shoping.updateMany(
    {},
    {
        $push: {
            coupons: {
                $each: ['90%', '70%'],
                $position: 0
            }
        }
    }
)

$slice: 배열의 크기를 결정한다.
db.shoping.updateMany(
    {},
    {
        $push: {
            coupons: {
                $each: ['15%', '20%'],
                $position: 0,
                $slice: 5
            }
        }
    }
)

$sort : 정렬한다. 1은 오름차순, -1은 내림차순
내림차순으로 정렬해서 넣기 때문에 99%가 먼저 들어간다.
db.shoping.updateMany(
    {},
    {$set: {"coupons": ['0%', '1%']}}
)

db.shoping.updateMany(
    {},
    {
        $push: {
            coupons: {
                $each: ['91%', '99%', '98%', '97%', '96%'],
                $position: -1,
                $sort: -1,
                $slice: 5
            }
        }
    }
)
```
pop이나 push는 배열의 끝만 조작하기 때문에 배열이 클 경우도 문제 없지만,  
pull이나 addToSet은 배열 전체를 조회하기 때문에 성능이 좋지 않다.  

### 문제
1. sample_mflix 데이터베이스의 movies collection 전체를 조회한다.
```java
use sample_mflix
db.movies.find()
```
2. movies collection의 Document 수를 구한다.
```java
db.movies.countDocuments()
db.movies.estimatedDocumentCount()
```

3. movies collection에서 전체를 조회하는데 title, year, genres, runtime, rated필드를 출력하고 _id필드는 출력하지 않는다.
```java
db.movies.find(
        {},
        {
            title: 1,
            year: 1,
            genres: 1,
            runtime: 1,
            rated: 1,
            _id: 0
        }
)
```
4. movies collection에서 runtime이 100분 이하인 Document를 조회한다.
```java
db.movies.find(
        {runtime: {$lt: 100}},
)
```
5. movies collection에서 runtime이 100분 이하이고 genres에 Drame가 포함되는 Document를 조회한다.
```java
db.movies.find(
        {
            runtime: {$lt: 100},
            genres: "Drama"
        },
)
```
6. movies collection에서 runtime이 100분 이하이고 genres가 유일하게 Drama인 Document를 조회한다.
```java
db.movies.find(
        {
            $and: [
                {runtime:{$lt:100}},
                {genres:"Drama"},
                {genres:{$size:1}}
            ]
        },
        { genres: 1}
)
```
7. movies collection에서 runtime이 100분 이하이고 type이 series가 아니고 개봉년도가 2015년 이상이거나 1925년 이하인 영화를 찾는다.
```java
db.movies.find(
        {
            $and: [
                {runtime:{$lt:100}},
                {type: { $ne : 'series'}},
                { $or: [
                    {year: {$gt: 2015}},
                    {year: {$lt: 1925}}
                ]}
            ]
        },
        { 
            year: 1,
            type: 1,
            runtime: 1
        }
).sort({year: 1})
```
8. movies collection에서 viewer 평가가 4.5 이상이거나 critic 평가가 9.5 이상인 영화를 찾고 runtime이 가장 긴 순서대로 5개 document를 출력한다.
필드는 title, runtime, tomatoes, _id 필드를 출력한다.
```java
db.movies.find({
        $or:[
                { "tomatoes.viewer.rating": { $gt:4.5 } },
                { "tomatoes.critic.rating": { $gt:9.5 } },
        ],
        
        },
        {
            title:1,
            runtime:1,
            tomatoes:1,
        }
).sort({runtime:-1}).limit(5)
```

9. sample_restuarants database의 restaurants collection에서 Queens에 있는 음식점 중에, A grade가 없는 음식점을 찾는다.
```java
db.restaurants.find(
        {
            borough: "Queens",
            "grades.grade": {$ne: 'A'},
            // grades: {$size: 3}
        },
        {
            grades: 1, _id: 0
        }
)
```


10. restaurants collection에서 Queens에 있는 음식점 중에, A와 Z가 같이 있는 음식점을 찾는다.
```java
db.restaurants.find({
        $and: [
            {borough:"Queens",},
            { grades: { $elemMatch: {grade: 'A'}}},
            { grades: { $elemMatch: {grade: 'Z'}}}
        ]
})
```

11. restaurants collection에서 Queens에 있는 음식점 중에, grades의 score가 하나라도 70 이상인 document를 조회하고 grades 배열에는 70이 넘는 document 하나만 출력한다.(나머지 필드는 그대로 출력한다.)
```java
db.restaurants.find(
        {
            borough:"Queens",
            "grades.score": { $gte: 70},
        
        },
        {
            address: 1,
            borough: 1,
            cuisine: 1,
            "grades.$": 1,
            name: 1,
            restaurant_id: 1
        }
)
```

## Aggregation Framework
- Collection의 데이터를 변환하거나 분석하기 위해 사용하는 집계 Framework
- Aggregation은 Find함수로 처리할 수 없는, SQL의 Group By와 Join 구문 같은 복잡한 데이터 분석 기능들을 제공한다.
- Aggregation Framework는 파이프라인 형태를 갖춘다. (Linux Pipeline 구문과 비슷)
- MongoDB 2.2부터 제공되었고 이전에는 MapReduce를 사용했다.

```SQL
SELECT 
    productName,
    SUM(quantity) AS sumQuantity
FROM orders
WHERE status = 'urgent'
GROUP BY productName

db.orders.aggregate([
    {$match: {status: 'urgent'}},
    {$group: {_id: '$productName', sumQuantity: {$sum: '$quantity'}}}
])
```

Input -> Match -> Group -> sort -> Output 형태로 데이터를 반환받는다.  
db.orders을 통해 컬렉션의 값을 가져온다.
```java
{_id: 0, productName: "A", quantity: 10, status: "new"},
{_id: 1, productName: "A", quantity: 20, status: "urgent"},
{_id: 2, productName: "A", quantity: 30, status: "urgent"},
{_id: 3, productName: "B", quantity: 40, status: "urgent"},
{_id: 4, productName: "B", quantity: 40, status: "urgent"},
{_id: 5, productName: "B", quantity: 40, status: "new"},
```
$match를 통해 status가 urgent인 데이터만 가져온다.
```java
{_id: 1, productName: "A", quantity: 20, status: "urgent"},
{_id: 2, productName: "A", quantity: 30, status: "urgent"},
{_id: 3, productName: "B", quantity: 40, status: "urgent"},
{_id: 4, productName: "B", quantity: 40, status: "urgent"},
```
$group을 통해 productName을 기준으로 그룹핑하고, quantity를 합산한다.
```java
{_id: "A", sumQuantity: 50},
{_id: "B", sumQuantity: 80},
```

## Aggregation Framework 사용법
https://mongodb.com/docs/v5.0/reference/operator/aggregation-pipeline/
```java
db.orders.insertMany( [
	{ _id: 0, name: "Pepperoni", size: "small", price: 19,
	  quantity: 10, date: ISODate( "2021-03-13T08:14:30Z" ) },
	{ _id: 1, name: "Pepperoni", size: "medium", price: 20,
	  quantity: 20, date : ISODate( "2021-03-13T09:13:24Z" ) },
	{ _id: 2, name: "Pepperoni", size: "large", price: 21,
	  quantity: 30, date : ISODate( "2021-03-17T09:22:12Z" ) },
	{ _id: 3, name: "Cheese", size: "small", price: 12,
	  quantity: 15, date : ISODate( "2021-03-13T11:21:39.736Z" ) },
	{ _id: 4, name: "Cheese", size: "medium", price: 13,
	  quantity:50, date : ISODate( "2022-01-12T21:23:13.331Z" ) },
	{ _id: 5, name: "Cheese", size: "large", price: 14,
	  quantity: 10, date : ISODate( "2022-01-12T05:08:13Z" ) },
	{ _id: 6, name: "Vegan", size: "small", price: 17,
	  quantity: 10, date : ISODate( "2021-01-13T05:08:13Z" ) },
	{ _id: 7, name: "Vegan", size: "medium", price: 18,
	  quantity: 10, date : ISODate( "2021-01-13T05:10:13Z" ) }
])
```

```java
이름별 수량 확인
db.orders.aggregate([
    {
        $match: {
            size: "medium"
        }
    },
    {
        $group: { // 그룹핑을 한다.
            _id: { $getField: "name"}, // name을 기준으로 그룹핑한다.
            totalQuantity: {  // totalQuantity라는 필드를 만들고
                $sum: {$getField: "quantity"} // quantity를 합산한다.
            }
        }
    }
])

$getField를 $로 사용할 수 있다.
db.orders.aggregate([
    {
        $match: {
            size: "medium"
        }
    },
    {
        $group: {
            _id: "$name", // $getField를 $로 사용할 수 있다.
            totalQuantity: { 
                $sum: "$quantity"
            }
        }
    }
])

db.orders.aggregate([
    {
        $match: {
            date: {
                $gte: new ISODate("2020-01-30"),
                $lt: new ISODate("2022-01-30")
            }
        }
    },
    {
        $group: {
            _id: {
                $dateToString: {
                    format: "%Y-%m-%d", date: "$date"
                }
            },
            totalOrderValue: {
                $sum: {
                    $multiply: ["$price", "$quantity"]
                }
            },
            averageOrderQuantity: {
                $avg: "$quantity"
            }
        }
    },
    {
        $sort: {
            totalOrderValue: -1
        }
    }
])

 db.books.insertMany([
	{ "_id" : 8751, "title" : "The Banquet", "author" : "Dante", "copies" : 2 },
	{ "_id" : 8752, "title" : "Divine Comedy", "author" : "Dante", "copies" : 1 },
	{ "_id" : 8645, "title" : "Eclogues", "author" : "Dante", "copies" : 2 },
	{ "_id" : 7000, "title" : "The Odyssey", "author" : "Homer", "copies" : 10 },
	{ "_id" : 7020, "title" : "Iliad", "author" : "Homer", "copies" : 10 }
 ])

// author로 그룹핑하여 books 필드에 title을 넣어준다.
db.books.aggregate([
    {
        $group: {
          _id: "$author",
          books: {
            $push: "$title"
          }
        }
    }
])

//$$ROOT : 시스템 변수 가장 탑레벨 도큐먼트의 정보를 넣어준다. -> 위에서는 그룹핑해여 나온 title을 넣어주지만 $$ROOT를 사용하면 전체 도큐먼트를 넣어준다.
db.books.aggregate([
    {
        $group: {
            _id: "$author", 
            books: {
                $push: "$$ROOT"
            }
        }
    }
])
        
db.books.aggregate([
    {
        $group: {
          _id: "$author",
          books: {
            $push: "$$ROOT"
          },
          totalCopies: {
            $sum: "$copies"
          }
        }
    }
])

// $addFields : 필드를 추가할때 사용한다.
db.books.aggregate([
    {
        $group: {
          _id: "$author",
          books: {
            $push: "$$ROOT"
          }
        }
    },
    {
        $addFields: {
            totalCopies: { $sum: "$books.copies"}
        }
    }
])

join 사용

db.orders.drop()

db.orders.insertMany([
    { "productId" : 1,   "price" : 12,   },
    { "productId" : 2,   "price" : 20,   },
    { "productId" : 3,   "price" : 80,   }
])

 
db.products.insertMany([
    { "id" : 1,  "instock" : 120 },  
    { "id" : 2,  "instock" : 80  },
    { "id" : 2,  "instock" : 90  },
    { "id" : 3,  "instock" : 60  }, 
    { "id" : 4,  "instock" : 70  }
])

db.orders.aggregate([
    {
        $lookup: { // left join
            from: 'products', // join할 컬렉션
            localField: 'productId', // orders의 productId
            foreignField: 'id', // products의 id
            as: 'data' // 조회 결과를 담을 필드
        }
    }
])

$expr: 조건을 표현할 때 사용한다.
같은필드를 기준으로 비교할때 사용한다.
하지만 다음과 같이 '$data.instock'과 같은 배열을 비교할때는 사용할 수 없다.
db.orders.aggregate([
    {
        $lookup: {
            from: 'products',
            localField: 'productId',
            foreignField: 'id',
            as: 'data'
        }
    },
    {
        $match: {
            $expr: {
                $gt: ['$data.instock', '$price']
            }
        }
    }
])

해결방법은 $unwind를 사용해서 배열을 풀어준다. -> order 기준으로 products는 1:N 관계이기 때문에 배열로 조회된다.
그렇기 때문에 
db.orders.aggregate([
    {
        $lookup: {
            from: 'products',
            localField: 'productId',
            foreignField: 'id',
            as: 'data'
        }
    },
    {
        $unwind: '$data'
    },
    {
        $match: {
            $expr: {
                $gt: ['$data.instock', '$price']
            }
        }
    }
])

db.orders.aggregate([
    {
        $lookup: {
            from: 'products',
            localField: 'productId',
            foreignField: 'id',
            as: 'data'
        }
    },
    {
        $unwind: '$data'
    },
    {
        $match: {
            $expr: {
                $lt: ['$data.instock', '$price']
            }
        }
    }
])

샘플링($sample) : 데이터를 랜덤하게 가져온다.
db.listingsAndReviews.aggregate([
    {
        $sample: {
            size: 3
        }
    },
    {
        $project: {
            name: 1,
            summary: 1
        }
    }
])

skip, limit : 페이징처리
db.listingsAndReviews.aggregate([
    {
        $match: {
            property_type: "Apartment"
        }
    },
    {
        $sort: {
            number_of_reviews: -1
        }
    },
    {
        $skip: 0
    },
    {
        $limit: 5
    },
    {
        $project: {
            name: 1,
            number_of_reviews: 1
        }
    }
])

$out: 조회 결과를 새로운 컬렉션으로 저장하는 법
$merge: 조회 결과를 새로운 컬렉션으로 저장하는 법
db.books.aggregate([
    {
        $group: {
            _id: "$author",
            books: {
                $push: "$title"
            }
        }
    },
    {
        $out: "authors"
    } 
])
```

## Aggregation Framework로 데이터 정제하기
```
결과
{
    _id:391,
    scores:[
        { type: "exam", avg_score: 50.0 },
        { type: "quiz", avg_score: 31.0 }
    ]
}

db.grades.aggregate([
    {
        $unwind: "$scores"
    },
    {
        $match: {
            "scores.type": {
                $in: ["exam", "quiz"]
            }
        }
    },
    {
        $group: {
            _id: {
                class_id: "$class_id",
                type: "$scores.type"
            },
            avgScore: {
                $avg: "$scores.score"
            }
        }
    },
    {
        $group: {
            _id: "$_id.class_id",
            scores: {
                $push: {
                    type: "$_id.type",
                    avg_score: "$avgScore"
                }
            }
        }
    },
    {
        $sort: {
            "_id": -1
        }
    },
    {
        $limit: 5
    }
])
```
1. $unwind : 배열을 풀어준다.
2. $match : stores.type 중에서 exam, quiz만 조회한다.
3. $group : class_id와 type을 기준으로 그룹핑하고, score의 평균을 구한다.
4. $group : 3번의 결과를 가지고 class_id를 기준으로 그룹핑하고, scores를 배열로 만든다.
5. $sort : class_id를 기준으로 내림차순 정렬한다.
6. $limit : 5개만 조회한다.

```java
db.grades.aggregate([
    {
        $addFields: {
            tmp_scores: {
                $filter: {
                    input: "$scores",
                    as: "score_var",
                    cond: {
                        $in: ["$$score_var.type", ["exam", "quiz"]]
                    }
                }
            }
        }
    },
    {
        $unset: ["scores", "student_id"]    // 필드 삭제
    },
    {
        $unwind: "$tmp_scores"
    },
    {
        $group: {
            _id: "$class_id",
            exam_scores: {
                $push: {
                    $cond: { // 조건에 맞는 데이터만 가져온다.
                        if: {
                            $eq: ["$tmp_scores.type", "exam"]
                        },
                        then: "$tmp_scores.score",
                        else: "$$REMOVE"
                    }
                }    
            },
            quiz_scores: {
                $push: {
                    $cond: {
                        if: {
                            $eq: ["$tmp_scores.type", "quiz"]
                        },
                        then: "$tmp_scores.score",
                        else: "$$REMOVE"
                    }
                }
            }
        }
    },
    {
        $project: {
            _id: 1,
            scores: {
                $objectToArray: {
                    exam: {
                        $avg: "$exam_scores"
                    },
                    quiz: {
                        $avg: "$quiz_scores"
                    }
                }
            }
        }
    },
    {
        $sort: {
            _id: -1
        }
    },
    {
        $limit: 5
    }
])
```

유저 변수(내가 만든 변수)를 사용할때는 $$를 사용한다.
1. $addFields : tmp_scores 필드로 추가 한다. tmp_scores 필드는 scores 필드에서 type이 exam, quiz인 데이터만 가져온다.
2. $unset : scores, student_id 필드를 삭제한다.
3. $unwind : tmp_scores 필드를 풀어준다.
4. $group : class_id를 기준으로 그룹핑하고, exam_scores와 quiz_scores를 배열로 만든다.
5. $project : _id와 scores 필드를 만든다. scores 필드는 exam_scores와 quiz_scores를 평균을 구해서 exam과 quiz 필드로 만든다.
6. $sort : _id를 기준으로 내림차순 정렬한다.
7. $limit : 5개만 조회한다.

## Replica Set 와 Sharded Cluster 에 따른 CURD 특징
### Target Query
쿼리할때 어느 샤드에 존재하는지 알고 쿼리하는 방식이다.  
Replica Set에서는 하나의 샤드에만 쿼리를 보내면 되지만, Sharded Cluster에서는 샤드마다 데이터가 분산되어 있기때문에 쿼리를 보내야하는 샤드를 알아야한다.

### Broadcast Query
모든 샤드에 쿼리를 보내는 방식이다.  
Range shard key를 사용하면 정렬이 되어서 저장되기 때문에 사용할 수 있지만
Hash shard key를 사용하면 정렬이 되어있지 범위 조건에서 모든 샤드를 쿼리 해야 한다.

### Updating Shard Keys
Version 4.0 이하는 Shard Key를 변경할 수 없다.
Version 4.2 이상에서는 Shard Key의 모든 필드는 Query Filter에 넣어야 수정할 수 있다.(Multi-Update는 불가능)

### Deleting with Shard Keys
Shard Key: {a:1, b:1} 일때 
동등 조건으로 _id를 사용하거나 db.test.deleteOne({_id: ObjectId("5f9f7f7f7f7f7f7f7f7f7f7f")})  
db.test.deleteOne({a: 1, b: 1}) 모든 조건으로 삭제할 수 있다.
