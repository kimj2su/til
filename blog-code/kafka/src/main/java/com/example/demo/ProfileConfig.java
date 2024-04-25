package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {

    @Bean
    @Profile("local")
    public String local() {
        return "local";
    }

    @Bean
    @Profile("dev")
    public String dev() {
        return "dev";
    }
}
