package com.example.group.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JavaUserRepository extends JpaRepository<JavaUser, Long> {

  Optional<JavaUser> findByName(String name);

}
