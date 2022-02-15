package com.example.mysql_concurrency.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

//    @Override
//    @Lock(LockModeType.OPTIMISTIC)
//    Optional<Team> findById(Long id);
}
