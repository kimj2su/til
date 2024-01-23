# lookup
몽고 디비는 RDB보다 조인의 성능이 느리다.  
그래서 조인을 사용하지 않고, 데이터를 중복해서 저장한다.  
또는 DB Layer에서 Join보다 Application Layer에서 Join을 하는 것이 더 효율적이다.

https://www.mongodb.com/docs/v5.0/reference/operator/aggregation/lookup/

## $lookup의 종류
1. Equality Match with a Sigle Join Condition
localField와 foreignField를 두고  output array field에 저장한다.
```java
{
   $lookup:
     {
       from: <collection to join>,
       localField: <field from the input documents>,
       foreignField: <field from the documents of the "from" collection>,
       as: <output array field>
     }
}
```

2. Join Conditions and Subqueries on a Joined Collection
예를 들어 where절에서 a.name = b.name 처럼 조건을 줄 수 있다.
```java
{
   $lookup:
      {
         from: <joined collection>,
         let: { <var_1>: <expression>, …, <var_n>: <expression> },
         pipeline: [ <pipeline to run on joined collection> ],
         as: <output array field>
      }
}

```

3. Correlated Subqueries Using Concise Syntax
5.0 버전에서 나온 간편한 문법이다.  
조인하려는 필드에 대해서 local, foreign 필드로 넣어서 할 수 있다.
```java
{
   $lookup:
      {
         from: <foreign collection>,
         localField: <field from local collection's documents>,
         foreignField: <field from foreign collection's documents>,
         let: { <var_1>: <expression>, …, <var_n>: <expression> },
         pipeline: [ <pipeline to run> ],
         as: <output array field>
      }
}
```

## $lookup
left outer join과 비슷한 기능을 한다.
```java
use stores
        
db.restaurants.insertMany( [
   {
      _id: 1,
      name: "American Steak House",
      food: [ "filet", "sirloin" ],
      beverages: [ "beer", "wine" ]
   },
   {
      _id: 2,
      name: "Honest John Pizza",
      food: [ "cheese pizza", "pepperoni pizza" ],
      beverages: [ "soda" ]
   }
] )

db.orders.insertMany( [
   {
      _id: 1,
      item: "filet",
      restaurant_name: "American Steak House"
   },
   {
      _id: 2,
      item: "cheese pizza",
      restaurant_name: "Honest John Pizza",
      drink: "lemonade"
   },
   {
      _id: 3,
      item: "cheese pizza",
      restaurant_name: "Honest John Pizza",
      drink: "soda"
   }
] )
```

### Equality Match with a Sigle Join Condition
```java
db.restaurants.aggregate( [
    {
        $lookup: {
            from: "orders",
            localField: "name",
            foreignField: "restaurant_name",
            as: "orders"
        }
    }
] )

db.restaurants.aggregate( [
    {
        $lookup: {
            from: "orders",
            localField: "name",
            foreignField: "restaurant_name",
            as: "orders"
        }
    },
    {
        $unwind: "$orders"
    },
    {
        $match: {
            $expr: {
                $and: [
                    {$in: ["$orders.item", "$food"]},
                    {$in: ["$orders.drink", "$beverages"]}
                ]
            }
        }
    },
    {
        $group: {
            _id: "$_id",
            name: {$first: "$name"},
            food: {$first: "$food"},
            beverages: {$first: "$beverages"},
            orders: {$push: "$orders"}
        }
    }
] )
```

### Join Conditions and Subqueries on a Joined Collection
```java
db.restaurants.aggregate( [
    {
        $lookup: {
            from: "orders",
            let: {
                name_var: "$name",
                beverages_lst: "$beverages",
                food_lst: "$food"
            },
            pipeline: [
                {
                    $match: {
                        $expr: {
                            $and: [
                                {
                                    $eq: [
                                        "$$name_var",
                                        "$restaurant_name"
                                    ]
                                },
                                {
                                    $in: [
                                        "$drink",
                                        "$$beverages_lst"
                                    ]
                                },
                                {
                                    $in: [
                                        "$item",
                                        "$$food_lst"
                                    ]
                                }
                            ]
                        }
                    }
                }
            ],
            as: "orders"
        }
    }
] )
```

### Correlated Subqueries Using Concise Syntax
```
5.0 버전에서 나온 간편한 문법이다.

db.restaurants.aggregate( [
    {
        $lookup: {
            from: "orders",
            localField: "name",
            foreignField: "restaurant_name",
            let: {
                beverages_lst: "$beverages",
                food_lst: "$food"
            },
            pipeline: [
                {
                    $match: {
                        $expr: {
                            $and: [
                                {
                                    $in: [
                                        "$drink",
                                        "$$beverages_lst"
                                    ]
                                },
                                {
                                    $in: [
                                        "$item",
                                        "$$food_lst"
                                    ]
                                }
                            ]
                        }
                    }
                }
            ],
            as: "orders"
        }
    }
] )
```

# 지역 기반 쿼리(geospatial query)
- https://docs.mongodb.com/manual/geospatial-queries/
- 위치, 지역을 기반으로 해서 쿼리를 할 수 있는 기능을 제공한다.
- 택시 도메인을 예를 들면, 서울시, 판교 택시만 가져오는 쿼리를 할 수 있다.
- GeoJSON을 사용한다.
- legacy coordinate pairs를 사용할 수 있다. -> [longitude, latitude] 두개의 좌표를 .으로 표현한다.

## GeoJSON
- Point -> {type: "Point", coordinates: [x, y]}
- LineString -> {type: "LineString", coordinates: [[x1, y1], [x2, y2]]}
- Polygon -> {type: "Polygon", coordinates: [[[x1, y1], [x2, y2], [x3, y3], [x4, y4]]]}

### $geoIntersects
- 지정한 지역과 겹치는 데이터를 가져온다.

### $geoWithin
- 지정한 지역 안에 있는 데이터를 가져온다.

### $near
- 지정한 지역에서 가까운 데이터를 가져온다.
- 평면 좌표계를 사용한다.

### $nearSphere
- 지정한 지역에서 가까운 데이터를 가져온다.
- 구면 좌표계를 사용한다.

```mongodb-json
db.grids.insertMany([
    {
        _id: 1,
        loc: [0,0]
    },
    {
        _id: 2,
        loc: [0,5]
    },
    {
        _id: 3,
        loc: [5,5]
    },
    {
        _id: 4,
        loc: [5,0]
    },
    {
        _id: 5,
        loc: {
            type: "Point",
            cooridinates: [5,5]
        }
    },
    {
        _id: 6,
        loc: {
            type: "Point",
            cooridinates: [5,0]
        }
    },
    {
        _id: 7,
        loc: {
            type: "LineString",
            cooridinates: [
                [6,6],
                [15,13]
            ]
        }    
    },
    {
        _id: 8,
        loc: {
            type: "LineString",
            cooridinates: [
                [0,12],
                [5,12]
            ]
        }    
    },
    {
        _id: 9,
        loc: {
            type: "Polygon",
            cooridinates: [
                [
                    [2,2],
                    [3,3],
                    [4,2],
                    [2,2]
                ]
            ]
        }    
    },
    {
        _id: 10,
        loc: {
            type: "Polygon",
            cooridinates: [
                [
                    [9,0],
                    [5,13],
                    [14,6],
                    [2,2]
                ]
            ]
        }    
    }
])
```

### 교차 조회
```mongodb-json
db.grids.find({
    loc: {
        $geoIntersects: {
            $geometry: {
                type: "Polygon",
                coordinates: [
                    [
                        [0,0],
                        [0,10],
                        [10,10],
                        [10,0],
                        [0,0]
                    ]
                ]
            }
        }
    }
})
```

### $geoWithin 조회
```mongodb-json
db.grids.find({
    loc: {
        $geoWithin: {
            $geometry: {
                type: "Polygon",
                coordinates: [
                    [
                        [0,0],
                        [0,10],
                        [10,10],
                        [10,0],
                        [0,0]
                    ]
                ]
            }
        }
    }
})
```

### $near 조회
- 지역기반 인덱스를 생성해야 한다.
```mongodb-json
db.grids.find({
    loc: {
        $near: {
            $geometry: {
                type: "Point",
                coordinates: [5,5]
            },
            $maxDistance: 3 // 좌표상 3이내
            
        }
    }
}) -> 안됨

db.grids.find({
    loc: {
        $near: [5,5],
        $maxDistance: 3 // 좌표상 3이내    
    }
})
```

# 지역기반 인덱스
- 2d : 평면 좌표계 -> 타입이 legacy coordinate pairs만 가능하다.
- 2dsphere : 구면 좌표계 -> 타입이 GeoJSON만 가능하다. 다 가능함, 단위가 미터이다. 그래서 엄청 큰 값이 나온다.
```mongodb-json
db.grids.createIndex({
    loc: "2d"
})

db.grids.createIndex({
    loc: "2dsphere"
})
```

# Change Streams
- https://docs.mongodb.com/manual/changeStreams/
- 데이터베이스의 변경사항을 실시간으로 감지하는 기능이다.
- 애플리케이션단에서 실시간으로 데이터에대한 변경사항을 직접 oplog를 읽지 않고 추적하고 변경하고 싶을때 사용한다.
- aggregation pipeline을 사용할 수 있다.
```java
@Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            MongoClient mongoClient = MongoClients.create(env.getProperty("spring.data.mongodb.uri"));
            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> collection = database.getCollection("inventory");

            // pipeline
            // Bson match = Aggregates.match(Filters.eq("operationType", "insert"));
            // Bson match = Aggregates.match(Filters.eq("operationType", "update"));
            Bson match = Aggregates.match(Filters.eq("fullDocument.status", true));
            List<Bson> pipeline = Arrays.asList(match);

            (1) MongoCursor<ChangeStreamDocument<Document>> cursor = mongoClient.watch().iterator(); // watch all databases
            (2) MongoCursor<ChangeStreamDocument<Document>> cursor = database.watch().iterator(); // watch all collections
            (3) MongoCursor<ChangeStreamDocument<Document>> cursor = collection.watch().iterator(); // watch a specific collection
            (4) MongoCursor<ChangeStreamDocument<Document>> cursor = collection.watch(pipeline).iterator(); // watch a specific collection with pipeline

            while (cursor.hasNext()) {
                ChangeStreamDocument<Document> next = cursor.next();
                System.out.println("Received a new message: " + next);
            }
        };
    }
```
- (1) watch all databases: 모든 데이터베이스의 변경사항을 감지한다.
- (2) watch all collections: 모든 컬렉션의 변경사항을 감지한다.
- (3) watch a specific collection: 특정 컬렉션의 변경사항을 감지한다.
- (4) watch a specific collection with pipeline: 특정 컬렉션의 변경사항을 감지하고, pipeline을 사용해서 변경사항을 필터링한다. -> 모든 데이터베이스, 모든 컬렉션 감지 가능


















