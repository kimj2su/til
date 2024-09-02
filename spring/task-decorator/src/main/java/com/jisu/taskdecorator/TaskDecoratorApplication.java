package com.jisu.taskdecorator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TaskDecoratorApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskDecoratorApplication.class, args);
  }

}
