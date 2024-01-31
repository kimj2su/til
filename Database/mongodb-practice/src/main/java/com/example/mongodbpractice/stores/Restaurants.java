package com.example.mongodbpractice.stores;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "restaurants")
public class Restaurants {

  @Id
  private Integer id;

  private String name;
  private List<String> food;
  private List<String> beverages;

  protected Restaurants() {
  }

  private Restaurants(Integer id, String name, List<String> food, List<String> beverages) {
    this.id = id;
    this.name = name;
    this.food = food;
    this.beverages = beverages;
  }

  public static Restaurants create(int id, String name, List<String> food, List<String> beverages) {
    return new Restaurants(id, name, food, beverages);
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<String> getFood() {
    return food;
  }

  public List<String> getBeverages() {
    return beverages;
  }
}
