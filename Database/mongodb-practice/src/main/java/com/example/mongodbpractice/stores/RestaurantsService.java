package com.example.mongodbpractice.stores;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RestaurantsService {

  private final RestaurantsRepository restaurantsRepository;

  public RestaurantsService(RestaurantsRepository restaurantsRepository) {
    this.restaurantsRepository = restaurantsRepository;
  }

  public Restaurants createRestaurant(Restaurants restaurant) {
    return restaurantsRepository.save(restaurant);
  }

  public List<RestaurantsOrdersLookupDto> aggregateRestaurantsOrdersLookup() {
    return restaurantsRepository.aggregateRestaurantsOrdersBasicLookup();
  }

  public List<RestaurantsOrdersLookupDto> aggregateRestaurantsOrdersUnwindLookup() {
    return restaurantsRepository.aggregateRestaurantsOrdersUnwindLookup();
  }

  public List<RestaurantsOrdersLookupDto> joinConditionsandSubqueriesonaJoinedCollection() {
    return restaurantsRepository.joinConditionsandSubqueriesonaJoinedCollection();
  }

  public List<RestaurantsOrdersLookupDto> aggregateRestaurantsOrdersCorrelatedSubqueries() {
    return restaurantsRepository.correlatedSubqueriesUsingConciseSyntax();
  }
}
