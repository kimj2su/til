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
