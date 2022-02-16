package com.example.mysql_concurrency.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface EntityRepository extends JpaRepository<Entity, Long> {

    @Override
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Lock(LockModeType.OPTIMISTIC)
    Optional<Entity> findById(Long id);
    Optional<Entity> findByUsername(String userName);
}
