package com.example.springbatchexample.part4;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Collection<Users> findAllByUpdatedDate(LocalDate updatedDate);
}
