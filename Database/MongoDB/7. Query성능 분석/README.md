 # Query Planner Logic - 쿼리가 어떻게 실행되는가?
 ```
 db.test.find( { a:1 } )
 ```
1. 처음 실행하면 응답이 느린다.
query -> disk -> chace -> return

2. 두번째 실행하면 응답이 빠르다.
query -> chace -> return

# Query plan
```java
cursor.explain()
```
## queryPlanner Mode
- 아무런 인자 없이 실행하면 queryPlanner Mode로 옵티마이저가 어떤 플랜을 선택했는지 확인할 수 있다.

## executionStats Mode
- executionStats Mode로 실행하면 queryPlanner Mode에서 보여주는 정보에 더해서 실제로 쿼리를 실행한 결과를 보여준다.
- 대부분의 경우 executionStats Mode를 사용한다.

## allPlansExecution Mode
- allPlansExecution Mode로 실행하면 queryPlanner Mode에서 보여주는 정보에 더해서 옵티마이저가 고려한 모든 플랜의 결과를 보여준다.
- allPlansExecution Mode는 테스트 목적으로 사용한다.

```mongodb-json
use sample_restaurants
db.restaurants.find(
    {
        borough: "Brooklyn"
    }
).explain("executionStats")

{
  explainVersion: '1',
  queryPlanner: {
    namespace: 'sample_restaurants.restaurants',
    indexFilterSet: false,
    parsedQuery: { borough: { '$eq': 'Brooklyn' } },
    queryHash: 'A1445303',
    planCacheKey: 'A1445303',
    maxIndexedOrSolutionsReached: false,
    maxIndexedAndSolutionsReached: false,
    maxScansToExplodeReached: false,
    winningPlan: {
      stage: 'COLLSCAN',
      filter: { borough: { '$eq': 'Brooklyn' } },
      direction: 'forward'
    },
    rejectedPlans: []
  },
  executionStats: {
    executionSuccess: true,
    nReturned: 6086,
    executionTimeMillis: 17,
    totalKeysExamined: 0,
    totalDocsExamined: 25359,
    executionStages: {
      stage: 'COLLSCAN',
      filter: { borough: { '$eq': 'Brooklyn' } },
      nReturned: 6086,
      executionTimeMillisEstimate: 4,
      works: 25360,
      advanced: 6086,
      needTime: 19273,
      needYield: 0,
      saveState: 25,
      restoreState: 25,
      isEOF: 1,
      direction: 'forward',
      docsExamined: 25359
    }
  },
  command: {
    find: 'restaurants',
    filter: { borough: 'Brooklyn' },
    '$db': 'sample_restaurants'
  },
  serverInfo: {
    host: 'ac-b8sxwh5-shard-00-01.1inggwg.mongodb.net',
    port: 27017,
    version: '6.0.13',
    gitVersion: '3b13907f9bdf6bd3264d67140d6c215d51bbd20c'
  },
  serverParameters: {
    internalQueryFacetBufferSizeBytes: 104857600,
    internalQueryFacetMaxOutputDocSizeBytes: 104857600,
    internalLookupStageIntermediateDocumentMaxSizeBytes: 16793600,
    internalDocumentSourceGroupMaxMemoryBytes: 104857600,
    internalQueryMaxBlockingSortMemoryUsageBytes: 33554432,
    internalQueryProhibitBlockingMergeOnMongoS: 0,
    internalQueryMaxAddToSetBytes: 104857600,
    internalDocumentSourceSetWindowFieldsMaxMemoryBytes: 104857600
  },
  ok: 1,
  '$clusterTime': {
    clusterTime: Timestamp({ t: 1706186960, i: 9 }),
    signature: {
      hash: Binary.createFromBase64('orqtMmTKrSrAc5Ksv24pCxQmXt0=', 0),
      keyId: Long('7289475519051988998')
    }
  },
  operationTime: Timestamp({ t: 1706186960, i: 9 })
}
```
- executionStats : 쿼리 실행에 대한 통계 정보
  - nReturned : 쿼리 결과로 반환된 도큐먼트 수
  - executionTimeMillis : 쿼리 실행에 소요된 시간
  - totalKeysExamined : 인덱스를 사용한 경우 인덱스를 스캔한 횟수 -> 0이면 인덱스를 사용하지 않은 것, collection scan을 한 것
  - totalDocsExamined : 쿼리 실행에 사용된 도큐먼트 수


- executionStages : 해당 필드에서 내부적으로 어떤 작업을 했는지 확인할 수 있다. 만약 스테이지가 여러개라면 stage마다 출력된다. 제일 안쪽의 스테이지부터 실행하게 된다.
  - stage : 쿼리 실행에 사용된 스테이지
  - filter : 쿼리 실행에 사용된 필터
  - nReturned : 쿼리 결과로 반환된 도큐먼트 수
  - executionTimeMillisEstimate : 쿼리 실행에 소요된 시간
  - works : 쿼리 실행에 사용된 도큐먼트 수
  - advanced : 쿼리 실행에 사용된 도큐먼트 수
  - needTime : 쿼리 실행에 사용된 도큐먼트 수
  - needYield : 쿼리 실행에 사용된 도큐먼트 수
  - saveState : 쿼리 실행에 사용된 도큐먼트 수
  - restoreState : 쿼리 실행에 사용된 도큐먼트 수
  - isEOF : 쿼리 실행에 사용된 도큐먼트 수
  - direction : 쿼리 실행에 사용된 도큐먼트 수
  - docsExamined : 쿼리 실행에 사용된 도큐먼트 수

```mongodb-json
db.restaurants.find(
    {
        borough: "Brooklyn"
    },
    {
        name: 1,
        borough: 1
    }
).explain("executionStats")

executionStages: {
      stage: 'PROJECTION_SIMPLE',
      nReturned: 6086,
      executionTimeMillisEstimate: 3,
      works: 25360,
      advanced: 6086,
      needTime: 19273,
      needYield: 0,
      saveState: 25,
      restoreState: 25,
      isEOF: 1,
      transformBy: { name: 1, borough: 1 },
      inputStage: {
        stage: 'COLLSCAN',
        filter: { borough: { '$eq': 'Brooklyn' } },
        nReturned: 6086,
        executionTimeMillisEstimate: 2,
        works: 25360,
        advanced: 6086,
        needTime: 19273,
        needYield: 0,
        saveState: 25,
        restoreState: 25,
        isEOF: 1,
        direction: 'forward',
        docsExamined: 25359
      }
    }
```
stage: 'PROJECTION_SIMPLE' -> projection이 적용된다는 것을 의미한다.
- inputStage로 안쪽 스테이지부터 실행된다.
- advanced : 부모 스테이지에 넘긴 도큐먼트 수
- nReturned : 쿼리 조건에 일치하는 도큐먼트 수
- works : 인덱스키 하나를 읽거나 도큐먼트하나를 패치하거나 정렬작업을 하거나등 쿼리 실행에 발생한 작업 단위를 잘게 쪼개서 얼마나 많은 작업들을 하는지 확인할 수 있다.

```mongodb-json
db.restaurants.find(
    {
        borough: "Brooklyn"
    },
    {
        name: 1,
        borough: 1
    }
).sort({name:1}).explain("executionStats")

executionStages: {
      stage: 'SORT',
      nReturned: 6086,
      executionTimeMillisEstimate: 8,
      works: 31447,
      advanced: 6086,
      needTime: 25360,
      needYield: 0,
      saveState: 31,
      restoreState: 31,
      isEOF: 1,
      sortPattern: { name: 1 },
      memLimit: 33554432,
      type: 'simple',
      totalDataSizeSorted: 778083,
      usedDisk: false,
      spills: 0,
      inputStage: {
        stage: 'PROJECTION_SIMPLE',
        nReturned: 6086,
        executionTimeMillisEstimate: 5,
        works: 25360,
        advanced: 6086,
        needTime: 19273,
        needYield: 0,
        saveState: 31,
        restoreState: 31,
        isEOF: 1,
        transformBy: { name: 1, borough: 1 },
        inputStage: {
          stage: 'COLLSCAN',
          filter: { borough: { '$eq': 'Brooklyn' } },
          nReturned: 6086,
          executionTimeMillisEstimate: 4,
          works: 25360,
          advanced: 6086,
          needTime: 19273,
          needYield: 0,
          saveState: 31,
          restoreState: 31,
          isEOF: 1,
          direction: 'forward',
          docsExamined: 25359
        }
      }
    }
```
제일 안쪽 스테이지부터 Collection Scan을 한 뒤에 PROJECTION 작업으로 transformBy를 통해 name과 borough만 가져온다.
최종적으로 sort를 하는것을 확인할 수 있다.  
위의 쿼리의 문제점은 컬렉션 스캔으로 모든 도큐먼트를 스캔한다. -> 인덱스를 걸어야한다.

```mongodb-json
db.restaurants.createIndex({borough:-1})
 
rejectedPlans: []
executionStats: {
    executionSuccess: true,
    nReturned: 6086,
    executionTimeMillis: 23,
    totalKeysExamined: 6086,
    totalDocsExamined: 6086,
    executionStages: {
      stage: 'SORT',
      nReturned: 6086,
      executionTimeMillisEstimate: 9,
      works: 12174,
      advanced: 6086,
      needTime: 6087,
      needYield: 0,
      saveState: 12,
      restoreState: 12,
      isEOF: 1,
      sortPattern: { name: 1 },
      memLimit: 33554432,
      type: 'simple',
      totalDataSizeSorted: 778083,
      usedDisk: false,
      spills: 0,
      inputStage: {
        stage: 'PROJECTION_SIMPLE',
        nReturned: 6086,
        executionTimeMillisEstimate: 5,
        works: 6087,
        advanced: 6086,
        needTime: 0,
        needYield: 0,
        saveState: 12,
        restoreState: 12,
        isEOF: 1,
        transformBy: { name: 1, borough: 1 },
        inputStage: {
          stage: 'FETCH',
          nReturned: 6086,
          executionTimeMillisEstimate: 5,
          works: 6087,
          advanced: 6086,
          needTime: 0,
          needYield: 0,
          saveState: 12,
          restoreState: 12,
          isEOF: 1,
          docsExamined: 6086,
          alreadyHasObj: 0,
          inputStage: {
            stage: 'IXSCAN',
            nReturned: 6086,
            executionTimeMillisEstimate: 3,
            works: 6087,
            advanced: 6086,
            needTime: 0,
            needYield: 0,
            saveState: 12,
            restoreState: 12,
            isEOF: 1,
            keyPattern: { borough: -1 },
            indexName: 'borough_-1',
            isMultiKey: false,
            multiKeyPaths: { borough: [] },
            isUnique: false,
            isSparse: false,
            isPartial: false,
            indexVersion: 2,
            direction: 'forward',
            indexBounds: { borough: [ '["Brooklyn", "Brooklyn"]' ] },
            keysExamined: 6086,
            seeks: 1,
            dupsTested: 0,
            dupsDropped: 0
          }
        }
      }
    }
  },
```
- nReturned: 6086
- executionTimeMillis: 23
- totalKeysExamined: 6086
- totalDocsExamined: 6086

전체키(totalKeysExamined)를 6086개를 조회하고, 전체 도큐먼트(totalDocsExamined)를 6086개를 조회한다.  
최종적으로 반환되는 수도 6086개로 효율적으로 쿼리를 실행한 것을 확인할 수 있다.

rejectedPlans: [] -> 실행 되지 않은 플랜(인덱스)이 없다는 것을 의미한다.
    
```mongodb-json
// 복합 인덱스 생성
db.restaurants.createIndex({ name:1, borough:-1 })
db.restaurants.find(
    {
        borough: "Brooklyn"
    },
    {
        name: 1,
        borough: 1
    }
).sort({name:1}).explain("executionStats")

rejectedPlans: [
      {
        stage: 'SORT',
        sortPattern: { name: 1 },
        memLimit: 33554432,
        type: 'simple',
        inputStage: {
          stage: 'PROJECTION_SIMPLE',
          transformBy: { name: 1, borough: 1 },
          inputStage: {
            stage: 'FETCH',
            inputStage: {
              stage: 'IXSCAN',
              keyPattern: { borough: -1 },
              indexName: 'borough_-1',
              isMultiKey: false,
              multiKeyPaths: { borough: [] },
              isUnique: false,
              isSparse: false,
              isPartial: false,
              indexVersion: 2,
              direction: 'forward',
              indexBounds: { borough: [ '["Brooklyn", "Brooklyn"]' ] }
            }
          }
        }
      }
    ]
    
db.restaurants.find(
    {
        borough: "Brooklyn"
    },
    {
        name: 1,
        borough: 1
    }
).sort({name:1}).explain("allPlansExecution")
```
allPlansExecution Mode로 실행하면 rejectedPlans에 있는 플랜도 실행한다.  
위의 플랜이 winningPlan, 밑에 플랜이 rejectedPlans이다.  
위의 쿼리를 조회하면 winningPlan의 nReturned는 101이고 rejectedPlans의 nReturned는 0이다.  
이게 무엇을 의미하는것이냐면 몽고 디비의 옵티마이저는 특정 규칙에 의해서 실행되다 보니 몇 가지 안전 제어 장치가 있다.  
그래서 실행 계획 후보를 전부 실행시키고 nReturned와 똑같이 반환하는 실행 계획을 winningPlan으로 선택한다.

```mongodb-json
db.restaurants.aggregate([
    {
        $match: { borough: "Brooklyn" }
    },
    {
        $group: {
            _id: "$cuisine",
            cnt: {$sum: 1 }
        }
    },
    {
        $sort: { name: 1 }
    }
]).explain("executionStats")

db.restaurants.aggregate([
    {
        $match: { borough: "Brooklyn" }
    },
    {
        $match: { cuisine: "American" }
    },
]).explain("executionStats")
위의 쿼리 실행계획을 보면 두개의 스테이지가 아니라 하나의 스테이지로 합쳐 쿼리한다.
db.restaurants.aggregate([
    {
        $match: { borough: "Brooklyn", cuisine: "American" }
    }
]).explain("executionStats")
같은 결과가 나온다.



db.restaurants.aggregate([
    {
        $group: {
            _id: { cuisine: "$cuisine", borough: "$borough" },
            cnt: {$sum: 1 }
        }
    },
    {
        $match: { "_id.borough": "Queens" }
    },
    {
        $sort: { "_id.borough": 1 }
    }
]).explain("executionStats")
위의 쿼리는 group을 먼저했지만 match를 먼저하고 group을 한다.
```

# Query 성능 최적화1(Aggregation)
- account에 대해서 symbolfh 그룹핑
- 회사 거래별 누적 수량
- 그중에서 상위 3개
- msft에 대한 값만 추출
- customer 정보와 account 정보도 함께 출력
```mongodb-json
use sample_analytics

db.transactions.aggregate([
    {
        $unwind: "$transactions"
    },
    {
        $group: {
            _id: {
                account_id: "$account_id",
                symbol: "$transactions.symbol"
            },
            currentHolding: {
                $sum: {
                    $cond: [
                        {
                            $eq: [
                                "$transactions.transaction_type",
                                "buy"
                            ]
                        },
                        "$transactions.amount",
                        {
                            $multiply: [
                              "$transactions.amount",
                            - 1
                            ]
                        }
                    ]
                }
            }    
        }  
    },
    {
        $match: {
            "_id.symbol": "msft"
        }
    },
    {
        $lookup: {
            from: "accounts",
            localField: "_id.account_id",
            foreignField: "account_id",
            as: "account_info",
            pipeline: [
                {
                    $lookup: {
                        from: "customers",
                        localField: "account_id",
                        foreignField: "accounts",
                        as: "customer_info",
                        pipeline: [
                            {
                                $project: {
                                    username: 1,
                                    _id: 0
                                }
                            }
                        ]
                    }
                },
                {
                    $project: {
                        _id: 0,
                        account_id: 0
                    }
                },
                {
                    $unwind: "$customer_info"
                }
            ]
        }
    },
    {
         $unwind: "$account_info"
    },
    {
        $project: {
            _id: 0,
            user: "$account_info.customer_info.username",
            account_info: "$_id.account_id",
            symbol: "$_id.symbol",
            currentHolding: 1,
            account_info: {
                limit: 1,
                products: 1
            }
        }
    },
    {
         $sort: {
                currentHolding: -1
         }
    },
    {
            $limit: 3
    }
]).explain('executionStats')



db.transactions.aggregate([
    {
        $unwind: "$transactions"
    },
    {
        $group: {
            _id: {
                account_id: "$account_id",
                symbol: "$transactions.symbol"
            },
            currentHolding: {
                $sum: {
                    $cond: [
                        {
                            $eq: [
                                "$transactions.transaction_type",
                                "buy"
                            ]
                        },
                        "$transactions.amount",
                        {
                            $multiply: [
                              "$transactions.amount",
                            - 1
                            ]
                        }
                    ]
                }
            }    
        }  
    },
    {
        $match: {
            "_id.symbol": "msft"
        }
    },
    {
         $sort: {
                currentHolding: -1
         }
    },
    {
            $limit: 3
    },
    {
        $lookup: {
            from: "accounts",
            localField: "_id.account_id",
            foreignField: "account_id",
            as: "account_info",
            pipeline: [
                {
                    $lookup: {
                        from: "customers",
                        localField: "account_id",
                        foreignField: "accounts",
                        as: "customer_info",
                        pipeline: [
                            {
                                $project: {
                                    username: 1,
                                    _id: 0
                                }
                            }
                        ]
                    }
                },
                {
                    $project: {
                        _id: 0,
                        account_id: 0
                    }
                },
                {
                    $unwind: "$customer_info"
                }
            ]
        }
    },
    {
         $unwind: "$account_info"
    },
    {
        $project: {
            _id: 0,
            user: "$account_info.customer_info.username",
            account_info: "$_id.account_id",
            symbol: "$_id.symbol",
            currentHolding: 1,
            account_info: {
                limit: 1,
                products: 1
            }
        }
    }
]).explain('executionStats')
```
위 두개의 쿼리 차이는 sort와 limit의 위치이다.  
sort와 limit의 위치를 바꾸면 쿼리 실행 계획이 달라진다.  
정렬이 안된 상태에서 $lookup으로 조인을 하면 조인된 테이블을 전체를 가져와서 정렬을 하고 limit을 걸어서 가져온다. -> 그렇기 대문에 totalDocsExamined가 데이터 수에 따라 몇배로 늘어난다.  
aggregate는 최대한 어떻게 수를 줄일 수 있을까에 대해 생각하고  가장 많이 실 수 하는게 상위 몇개, 하위 몇개를 가져오고 조인도 하는 경우에 $lookup 이후에 하느냐 전에 하느냐에 따라서 성능이 달라진다.  
만약 정렬하려는 필드가 가공한 필드가 아니고 컬렉션에 있는 원본 필드라면 룩업 전에 내부적으로 먼저 정렬을 해준다.


# Query 성능 최적화2(Index Bound)
- 멀티키 인덱스를 생성하고 나서 주의해야할 점

```mongodb-json
db.survey.insertMany([
    { item:  "ABC", ratings:  [2, 9], category_id:  10},
    { item:  "XYZ", ratings:  [4, 3], category_id:  20},
    { item:  "ABC", ratings:  [9], category_id:  20},
    { item:  "ABC", ratings:  [9, 10], category_id:  30},
    { item:  "ABC", ratings:  [2, 4], category_id:  30}
])

for (var i = 0; i < 15; i++) {
    arr = []
    db.survey.find({}, {_id: 0}).forEach(function(document) {
        arr.push(document)
    })
    db.survey.insertMany(arr)
}

db.survey.createIndex({category_id: 1})

// 범위 스캔을 했을때는 indexBounds를 확인해야 한다.
// indexBounds란 인덱스 속성들 중에 범위를 표시해주는 속성이다. -> 인덱스 키를 어디서부터 어디까지 탐색 했는지 범위를 표시해준다.
// 멀티키 인덱스일때는 기대와 다르게 동작할 수 있다.
db.survey.find({
    category_id: {
        $gt: 15,
        $lt: 25
    }
}).explain("executionStats")

db.survey.createIndex({ratings: 1})
db.survey.find({
    ratings: {
        $gte: 3,
        $lte: 6
    }
}).explain("executionStats")

stage: 'IXSCAN',
        keyPattern: { ratings: 1 },
        indexName: 'ratings_1',
        isMultiKey: true, // 멀티키 인덱스를 사용했다.
        multiKeyPaths: { ratings: [ 'ratings' ] },
        indexBounds: { ratings: [ '[-inf.0, 6]' ] }, // 문제의 지점 한쪽은 6이지만 다른 한쪽은 -inf.0(인피니트, 가장 작은 값까지) 이다. -> 이런식으로 인덱스를 탐색하면 안된다. 

3이상 6이하를 찾길 원했지만 멀티키 인덱스를 하면 한쪽 방향으로 쭉 탐색하는 문제가 방생할 수 있다.
몽고디비에서는 이걸 해결하기 위해 elemMatch를 사용한다.

db.survey.find({
    ratings: {
        $elemMatch: {
            $gte: 3,
            $lte: 6
        }
    }
}).explain("executionStats")

db.survey.find({
    ratings: {
        $gte: 3,
        $lte: 6
    }
}) -> 이 쿼리의 결과는 ratings 필드가 배열 일때 ratings 필드의 값이 3이상 6이하인 조건이 도큐먼트를 하나라도 만족하면 반환한다.
예를 들어 ratings: [2, 9] 이면 2는 6이하이고 9는 3이상이므로 조건을 만족한다.
ratings: [9]는 3보다 크지만 6이하가 아니므로 조건을 만족하지 못해 반환하지 않는다.

db.survey.find({
    ratings: {
        $elemMatch: {
            $gte: 3,
            $lte: 6
        }
    }
}) -> 이 쿼리의 결과는 각 요소마다 두 개의 조건을 모두 비교하여 하나라도 맞는 조건이 있으면 반환한다.
ratings: [4, 3] -> 4는 3이상 6이하이고 3은 3이상 6이하이므로 조건을 만족한다.
ratings: [9] -> 9는 3이상 6이하가 아니므로 조건을 만족하지 못해 반환하지 않는다.
ratings: [4, 2] -> 4는 3이상 6이하이므로 반환한다.

만약 3이상 6이하 모두 만족하는 조건을 확인하려면 다음과 같이 주면 된다.
db.survey.find({
    $and: [
        { ratings: { $not: { $lt: 3 } }},
        { ratings: { $not: { $gt: 6 } } }
    ]
}).explain("executionStats")
하지만 이렇게 하면 indexBounds: { ratings: [ '[MinKey, 6]', '(inf.0, MaxKey]' ] } 으로 인덱스 바운드가 막혀 있지 않다는 문제가 있다.

db.survey.find({
    $and: [
        { ratings: { $elemMatch: { $gte: 3, $lte: 6 }} },
        { ratings: { $not: { $lt: 3 } }},
        { ratings: { $not: { $gt: 6 } } }
    ]
}).explain("executionStats")
$elemMatch는 하나라도 만족하면 가져오기 때문에 일단 수를 줄여주고
$not을 사용하여 3이상 6이하가 아닌 것을 제외시켜 준다. -> IndexBounds가 잘 막혀 있다.
nReturned: 1 -> 1개를 반환했다.
totalKeyExamined: 3 -> 3개만 스캔했다.
totalDocsExamined: 2 -> 도큐먼트를 2개만 읽었다.
```
