package com.example.demo.kafka;

import java.lang.annotation.Documented;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "test-collection")
public class TestCollection {

  @Id
  private String id;

  private String name;

  public TestCollection(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
