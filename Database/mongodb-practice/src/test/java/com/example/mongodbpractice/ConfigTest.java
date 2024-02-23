package com.example.mongodbpractice;

import com.example.mongodbpractice.config.TestConfiguration;
import com.example.mongodbpractice.utils.AcceptanceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigTest  extends AcceptanceTest  {

    @Autowired
    private TestConfiguration testConfiguration;

    @Autowired
    private Environment environment;

    @Test
    void test() {
        assertThat(testConfiguration.getUri()).isEqualTo(environment.getProperty("com.example.dev.uri"));
        assertThat(testConfiguration.getDatabase()).isEqualTo(environment.getProperty("com.example.dev.database"));
    }
}
