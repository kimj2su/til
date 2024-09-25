package com.example.demo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration(proxyBeanMethods = false)
public class MongoConfig {

  @Value("${spring.data.mongodb.uri}")
  private String connectionString;

  @Bean
  public MongoDatabaseFactory mongoDatabaseFactory() {
    return new SimpleMongoClientDatabaseFactory(mongoClient(), "websocket");
  }

  @Bean
  public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
    return new MongoTemplate(mongoDatabaseFactory);
  }

//  @Bean
//  public MongoTemplate mongoTemplate() {
//    return new MongoTemplate(mongoClient(), "websocket");
//  }

  private MongoClient mongoClient() {
    return MongoClients.create(connectionString);
  }
}
