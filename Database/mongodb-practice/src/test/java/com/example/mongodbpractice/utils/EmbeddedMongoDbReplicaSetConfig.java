package com.example.mongodbpractice.utils;

import de.flapdoodle.embed.mongo.commands.MongodArguments;
import de.flapdoodle.embed.mongo.config.Storage;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

@TestConfiguration
public class EmbeddedMongoDbReplicaSetConfig {
    @Bean
    MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Bean
    MongodArguments mongodArguments() {
        return MongodArguments.builder()
                .replication(Storage.of("test", 10))
                .build();
    }
}
