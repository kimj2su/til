package com.example.mongodbpractice.utils;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
// import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
// import org.testcontainers.containers.MongoDBContainer;
// import org.testcontainers.junit.jupiter.Container;
// import org.testcontainers.junit.jupiter.Testcontainers;
// import org.testcontainers.utility.DockerImageName;
// import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
// import org.testcontainers.containers.MongoDBContainer;
// import org.testcontainers.junit.jupiter.Container;
// import org.testcontainers.junit.jupiter.Testcontainers;
// import org.testcontainers.utility.DockerImageName;

// @Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestMongoDBConfiguration.class)
public abstract class AcceptanceTest {

    @Autowired
    private DatabaseCleanup databaseCleanup;

    // @LocalServerPort
    // private int port;

    // @Container
    // @ServiceConnection
    // public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    // @BeforeAll
    // public static void start() {
    //     mongoDBContainer.start();
    // }

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();
    }
}
