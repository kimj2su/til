package com.example.mongodbpractice.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@ActiveProfiles("test")
// @TestConfiguration
public class MongoDBContainerTest {

    // @Container
    @ServiceConnection
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @BeforeAll
    public static void setup() {
        mongoDBContainer.start();
    }

    // @Test
    // void test() {
    //     System.out.println("MongoDBContainerTest.test()");
    // }

    // @Bean
    // @ServiceConnection
    // public MongoDBContainer mongoDBContainer() {
    //     return new MongoDBContainer(DockerImageName.parse("mongo:latest"));
    // }
}
