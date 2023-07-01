package com.example.multiplebeans.repository.primary;

import com.example.multiplebeans.entity.primary.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
