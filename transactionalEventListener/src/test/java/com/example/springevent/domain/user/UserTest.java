package com.example.springevent.domain.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.springevent.domain.user.UserStatus.*;
import static org.assertj.core.api.Assertions.*;


class UserTest {

    @DisplayName("유저를 생성시 유저 상태가 활성화이다.")
    @Test
    void createUser() {
        User user = User.builder()
                .userName("홍길동")
                .status(ACTIVE)
                .build();

        assertThat(user.getUserName()).isEqualTo("홍길동");
        assertThat(user.getStatus()).isEqualTo(ACTIVE);
    }

    @DisplayName("유저를 생성시 유저 상태가 비활성화이다.")
    @Test
    void createUser2() {
        User user = User.builder()
                .userName("홍길동")
                .status(INACTIVE)
                .build();

        assertThat(user.getUserName()).isEqualTo("홍길동");
        assertThat(user.getStatus()).isEqualTo(INACTIVE);
    }
}