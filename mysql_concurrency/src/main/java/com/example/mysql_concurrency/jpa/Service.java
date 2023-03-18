package com.example.mysql_concurrency.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {

    private final EntityRepository entityRepository;


    @Transactional
    public void create() {
        Optional<Entity> byId = entityRepository.findById(1L);
        if (byId.isEmpty()) {
            Entity jisu = Entity.builder()
                    .username("jisu")
                    .build();
            entityRepository.save(jisu);
        }
    }
}
