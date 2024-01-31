package com.example.mongodbpractice.stores;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersRepository extends MongoRepository<Orders, Integer> {

}
