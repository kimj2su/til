package com.example.mysql_concurrency.mybatis;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@javax.persistence.Entity
@OptimisticLocking
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Entity2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String lastAt;

    @Builder
    public Entity2(Long id, String username, String lastAt) {
        this.id = id;
        this.username = username;
        this.lastAt = lastAt;
    }
}
