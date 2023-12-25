package com.example.mongodbpractice.utils;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DatabaseCleanup {

    private final MongoTemplate mongoTemplate;

    public DatabaseCleanup(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void execute() {
        Set<String> collectionNames = mongoTemplate.getCollectionNames();
        mongoTemplate.getDb().drop();
        // for (String collectionName : collectionNames) {
        //     mongoTemplate.dropCollection(collectionName);
        // }
    }

}
