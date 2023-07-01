package com.example.multiplebeans.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@SpringBootTest
public class MultipleBeansTest {

    @Autowired
    private DataSource primaryDataSource;

    @Autowired
    private DataSource secondDataSource;

    @Autowired
    JPAQueryFactory primaryJpaQueryFactory;

    @Autowired
    JPAQueryFactory secondJpaQueryFactory;


    @Test
    void getDataSource() {
        System.out.println("primaryDataSource = " + primaryDataSource);
        System.out.println("secondDataSource = " + secondDataSource);
        System.out.println("primaryJpaQueryFactory = " + primaryJpaQueryFactory);
        System.out.println("secondJpaQueryFactory = " + secondJpaQueryFactory);
    }
}
