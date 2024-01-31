package com.example.mongodbpractice.stores;

import java.util.List;

public interface RestaurantsRepositoryCustom {

  // Equality Match with a Sigle Join Condition
  List<RestaurantsOrdersLookupDto> aggregateRestaurantsOrdersBasicLookup();
  List<RestaurantsOrdersLookupDto> aggregateRestaurantsOrdersUnwindLookup();

  // Join Conditions and Subqueries on a Joined Collection
  List<RestaurantsOrdersLookupDto> joinConditionsandSubqueriesonaJoinedCollection();

  // Correlated Subqueries Using Concise Syntax
  List<RestaurantsOrdersLookupDto> correlatedSubqueriesUsingConciseSyntax();
}
