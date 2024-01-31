package com.example.mongodbpractice.stores;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Orders {

  @Id
  private Integer id;

  private String item;

  private String restaurant_name;

  private String drink;

  public Orders() {
  }
  private Orders(Integer id, String item, String restaurant_name, String drink) {
    this.id = id;
    this.item = item;
    this.restaurant_name = restaurant_name;
    this.drink = drink;
  }

  public static Orders create(int id, String item, String restaurant_name, String drink) {
    return new Orders(id, item, restaurant_name, drink);
  }

  public Integer getId() {
    return id;
  }

  public String getItem() {
    return item;
  }

  public String getRestaurant_name() {
    return restaurant_name;
  }

  public String getDrink() {
    return drink;
  }
}
