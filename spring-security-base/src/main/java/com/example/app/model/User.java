package com.example.app.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
//@SQLDelete(sql = "UPDATE \"user\" SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

//    @PrePersist
//    void registeredAt() {
//        this.registeredAt = Timestamp.from(Instant.now());
//    }
//
//    @PreUpdate
//    void updatedAt() {
//        this.updatedAt = Timestamp.from(Instant.now());
//    }

    @PrePersist
    void registeredAt() {
        this.registeredAt = LocalDateTime.now();
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public static User of(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        return user;
    }
}