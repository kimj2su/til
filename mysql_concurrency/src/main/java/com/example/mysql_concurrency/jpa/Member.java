package com.example.mysql_concurrency.jpa;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
//@OptimisticLocking(type = OptimisticLockType.ALL)
@OptimisticLocking
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@DynamicUpdate
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;


    @Builder
    public Member(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public void modifyUsername(String username) {
        this.username = username;
    }
}
