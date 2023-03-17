package com.example.mysql_concurrency.mybatis;


import lombok.Builder;

public class Entity {

    private Long id;

    private String username;


    @Builder
    public Entity(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
