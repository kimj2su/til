package com.example.mysql_concurrency.jpa;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@javax.persistence.Entity
//@OptimisticLocking(type = OptimisticLockType.ALL)
@OptimisticLocking
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@DynamicUpdate
public class Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;


    @Builder
    public Entity(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
