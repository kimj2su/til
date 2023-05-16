package com.example.springevent.domain.user;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private UserStatus status;

    @Builder
    public User(String userName, UserStatus status) {
        this.userName = userName;
        this.status = status;
    }

    public void modifyStatus(UserStatus status) {
        this.status = status;
    }
}
