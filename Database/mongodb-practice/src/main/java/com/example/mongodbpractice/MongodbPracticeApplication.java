package com.example.mongodbpractice;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MongodbPracticeApplication {

    private final Environment env;

    public MongodbPracticeApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(MongodbPracticeApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            MongoClient mongoClient = MongoClients.create(env.getProperty("spring.data.mongodb.uri"));
            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> collection = database.getCollection("inventory");

            // pipeline
            // Bson match = Aggregates.match(Filters.eq("operationType", "insert"));
            // Bson match = Aggregates.match(Filters.eq("operationType", "update"));
            Bson match = Aggregates.match(Filters.eq("fullDocument.status", true));
            List<Bson> pipeline = Arrays.asList(match);

            // MongoCursor<ChangeStreamDocument<Document>> cursor = mongoClient.watch().iterator(); // watch all databases
            // MongoCursor<ChangeStreamDocument<Document>> cursor = database.watch().iterator(); // watch all collections
            // MongoCursor<ChangeStreamDocument<Document>> cursor = collection.watch().iterator(); // watch a specific collection
            MongoCursor<ChangeStreamDocument<Document>> cursor = collection.watch(pipeline).iterator(); // watch a specific collection with pipeline

            while (cursor.hasNext()) {
                ChangeStreamDocument<Document> next = cursor.next();
                System.out.println("Received a new message: " + next);
            }
        };
    }
}
