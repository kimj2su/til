package com.example.mongodbpractice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

// @Configuration
@ConfigurationProperties(prefix = "com.example.dev")
public class TestConfiguration {

    private String uri;

    private String database;

    @ConstructorBinding
    public TestConfiguration(String uri, String database) {
        this.uri = uri;
        this. database = database;
    }

    public String getUri() {
        return uri;
    }

    public String getDatabase() {
        return database;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public String toString() {
        return "TestConfiguration{" +
                "uri='" + uri + '\'' +
                ", database='" + database + '\'' +
                '}';
    }
}
