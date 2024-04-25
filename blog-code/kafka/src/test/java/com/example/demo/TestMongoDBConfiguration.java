package com.example.demo;


import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@TestConfiguration(proxyBeanMethods = false)
public class TestMongoDBConfiguration {


     @Bean
     public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory) {
         return new MongoTemplate(mongoDbFactory);
     }

     @Bean
     public MongoDatabaseFactory mongoDbFactory(MongoServer mongoServer) {
         String connectionString = mongoServer.getConnectionString();
         return new SimpleMongoClientDatabaseFactory(connectionString + "/test");
     }

     @Bean
     public MongoServer mongoServer() {
         MongoServer mongoServer = new MongoServer(new MemoryBackend());
         mongoServer.bind("localhost", 27017);
         return mongoServer;
     }
}
