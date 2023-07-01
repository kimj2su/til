package com.example.multiplebeans.repository.second;


import com.example.multiplebeans.entity.second.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
