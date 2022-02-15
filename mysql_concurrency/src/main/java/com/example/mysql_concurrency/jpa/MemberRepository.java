package com.example.mysql_concurrency.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Override
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Lock(LockModeType.OPTIMISTIC)
    Optional<Member> findById(Long id);


    Optional<Member> findByUsername(String userName);
}
