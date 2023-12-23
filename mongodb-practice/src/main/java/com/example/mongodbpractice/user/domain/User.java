package com.example.mongodbpractice.user.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private long id;

    private String name;
    private int age;

    private User(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public static User of(long id, String name, int age) {
        return new User(id, name, age);
    }

    public void modify(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
