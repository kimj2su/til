package com.example.group.libraryapp.dto.user.request;

public class JavaUserCreateRequest {
    private String name;
    private Integer age;

    public JavaUserCreateRequest(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

}
