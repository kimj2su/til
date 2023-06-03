package com.example.moduleapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication(
		scanBasePackages = {
				"com.example.moduleapi",
				"com.example.modulecommon"
		}
)
@EntityScan(basePackages = "com.example.modulecommon.domain")
@EnableJpaRepositories(basePackages = "com.example.modulecommon.repository")
public class ModuleApiApplication {
	
	@Value("${profile-name}")
	private String profileName;

	public static void main(String[] args) {
		SpringApplication.run(ModuleApiApplication.class, args);
	}

	@PostConstruct
	private void start() {
		System.out.println("profileName = " + profileName);
	}
}
