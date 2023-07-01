package com.example.multiplebeans.repository.primary;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaRepositoryCustom {

    private final JPAQueryFactory primaryJpaQueryFactory;
}
