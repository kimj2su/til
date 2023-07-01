package com.example.multiplebeans.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecondQueryDslConfig {

    @PersistenceContext(unitName = "secondEntityManager")
    private EntityManager em;

    @Bean
    public JPAQueryFactory secondJpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}