package com.example.demo.kafka;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<TestCollection, String> {

}
