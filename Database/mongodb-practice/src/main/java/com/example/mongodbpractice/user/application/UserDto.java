package com.example.mongodbpractice.user.application;

import com.example.mongodbpractice.user.domain.User;

public class UserDto {

    private long id;
    private String name;
    private int age;

    public UserDto(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User toEntity(long id) {
        return User.of(id, name, age);
    }

    public static User of(long id, String name, int age) {
        return User.of(id, name, age);
    }

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getName(), user.getAge());
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
