package com.example.mongodbpractice.stores;

import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

public class RestaurantsRepositoryCustomImpl implements
    RestaurantsRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  public RestaurantsRepositoryCustomImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  /**
   * <pre>
   *  db.restaurants.aggregate( [
   *     {
   *         $lookup: {
   *             from: "orders",
   *             localField: "name",
   *             foreignField: "restaurant_name",
   *             as: "orders"
   *         }
   *     }
   *  ] )
   *  </pre>
   */
  @Override
  public List<RestaurantsOrdersLookupDto> aggregateRestaurantsOrdersBasicLookup() {
    Aggregation aggregation = Aggregation.newAggregation(
        Aggregation.lookup("orders", "name", "restaurant_name", "orders")
    );

    mongoTemplate.aggregate(aggregation, "restaurants", Document.class)
        .getMappedResults();

    return mongoTemplate.aggregate(aggregation, "restaurants", RestaurantsOrdersLookupDto.class)
        .getMappedResults();
  }

  /**
   * <pre>
   *    db.restaurants.aggregate( [
   *     {
   *         $lookup: {
   *             from: "orders",
   *             localField: "name",
   *             foreignField: "restaurant_name",
   *             as: "orders"
   *         }
   *     },
   *     {
   *         $unwind: "$orders"
   *     },
   *     {
   *         $match: {
   *             $expr: {
   *                 $and: [
   *                     {$in: ["$orders.item", "$food"]},
   *                     {$in: ["$orders.drink", "$beverages"]}
   *                 ]
   *             }
   *         }
   *     },
   *     {
   *         $group: {
   *             _id: "$_id",
   *             name: {$first: "$name"},
   *             food: {$first: "$food"},
   *             beverages: {$first: "$beverages"},
   *             orders: {$push: "$orders"}
   *         }
   *     }
   * ] )
   * </pre>
   */
  @Override
  public List<RestaurantsOrdersLookupDto> aggregateRestaurantsOrdersUnwindLookup() {
    AggregationOperation matchOperation = context -> new Document("$match",
        new Document("$expr",
            new Document("$and",
                List.of(
                    new Document("$in", List.of("$orders.item", "$food")),
                    new Document("$in", List.of("$orders.drink", "$beverages"))
                )
            )
        )
    );
    Aggregation aggregation = Aggregation.newAggregation(
        Aggregation.lookup("orders", "name", "restaurant_name", "orders"),
        Aggregation.unwind("orders"),
        matchOperation,
        Aggregation.group("_id")
            .first("name").as("name")
            .first("food").as("food")
            .first("beverages").as("beverages")
            .push("orders").as("orders")
    );

    return mongoTemplate.aggregate(aggregation, "restaurants", RestaurantsOrdersLookupDto.class)
        .getMappedResults();
  }

  /**
   * <pre>
   *   db.restaurants.aggregate( [
   *     {
   *         $lookup: {
   *             from: "orders",
   *             let: {
   *                 name_var: "$name",
   *                 beverages_lst: "$beverages",
   *                 food_lst: "$food"
   *             },
   *             pipeline: [
   *                 {
   *                     $match: {
   *                         $expr: {
   *                             $and: [
   *                                 {
   *                                     $eq: [
   *                                         "$$name_var",
   *                                         "$restaurant_name"
   *                                     ]
   *                                 },
   *                                 {
   *                                     $in: [
   *                                         "$drink",
   *                                         "$$beverages_lst"
   *                                     ]
   *                                 },
   *                                 {
   *                                     $in: [
   *                                         "$item",
   *                                         "$$food_lst"
   *                                     ]
   *                                 }
   *                             ]
   *                         }
   *                     }
   *                 }
   *             ],
   *             as: "orders"
   *         }
   *     }
   * ] )
   * </pre>
   */
  @Override
  public List<RestaurantsOrdersLookupDto> joinConditionsandSubqueriesonaJoinedCollection() {
    final Map<String, String> letVariables = Map.of(
        "name_var", "$name",
        "beverages_lst", "$beverages",
        "food_lst", "$food"
    );

    AggregationOperation matchOperation = context -> new Document("$lookup",
        new Document("from", "orders")
            .append("let", new Document(letVariables))
            .append("pipeline", List.of(
                new Document("$match",
                    new Document("$expr",
                        new Document("$and",
                            List.of(
                                new Document("$eq", List.of("$$name_var", "$restaurant_name")),
                                new Document("$in", List.of("$drink", "$$beverages_lst")),
                                new Document("$in", List.of("$item", "$$food_lst"))
                            )
                        )
                    )
                )
            ))
            .append("as", "orders")
    );

    Aggregation aggregation = Aggregation.newAggregation(
        matchOperation
    );

    return mongoTemplate.aggregate(aggregation, "restaurants", RestaurantsOrdersLookupDto.class)
        .getMappedResults();
  }


  /**
   * <pre>
   *   db.restaurants.aggregate( [
   *     {
   *         $lookup: {
   *             from: "orders",
   *             localField: "name",
   *             foreignField: "restaurant_name",
   *             let: {
   *                 beverages_lst: "$beverages",
   *                 food_lst: "$food"
   *             },
   *             pipeline: [
   *                 {
   *                     $match: {
   *                         $expr: {
   *                             $and: [
   *                                 {
   *                                     $in: [
   *                                         "$drink",
   *                                         "$$beverages_lst"
   *                                     ]
   *                                 },
   *                                 {
   *                                     $in: [
   *                                         "$item",
   *                                         "$$food_lst"
   *                                     ]
   *                                 }
   *                             ]
   *                         }
   *                     }
   *                 }
   *             ],
   *             as: "orders"
   *         }
   *     }
   * ] )
   * </pre>
   */
  @Override
  public List<RestaurantsOrdersLookupDto> correlatedSubqueriesUsingConciseSyntax() {
    final Map<String, String> letVariables = Map.of(
        "food_lst", "$food",
        "beverages_lst", "$beverages"
    );

    AggregationOperation matchOperation = context -> new Document("$lookup",
        new Document("from", "orders")
            .append("localField", "name")
            .append("foreignField", "restaurant_name")
            .append("let", new Document(letVariables))
            .append("pipeline", List.of(
                new Document("$match",
                    new Document("$expr",
                        new Document("$and",
                            List.of(
                                new Document("$in", List.of("$drink", "$$beverages_lst")),
                                new Document("$in", List.of("$item", "$$food_lst"))
                            )
                        )
                    )
                )
            ))
            .append("as", "orders")
    );
    Aggregation aggregation = Aggregation.newAggregation(
        matchOperation
    );


    return mongoTemplate.aggregate(aggregation, "restaurants", RestaurantsOrdersLookupDto.class)
        .getMappedResults();
  }
}
