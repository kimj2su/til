package com.example.mongodbpractice.stores;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantsRepository extends MongoRepository<Restaurants, Integer>, RestaurantsRepositoryCustom {

}
