package com.jisu.testcodewitharchitecture.user.controller.response;

import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MyProfileResponseTest {

    @Test
    void User로_응답을_생성할_수_있다() {
        // given : 선행조건 기술
        User user = User.builder()
                .id(1L)
                .email("kimjisu3268@gmail.com")
                .nickname("jisu3268")
                .address("Pangyo")
                .status(UserStatus.ACTIVE)
                .certificationCode(UUID.randomUUID().toString())
                .lastLoginAt(100L)
                .build();

        // when : 기능 수행
        MyProfileResponse myProfileResponse = MyProfileResponse.from(user);

        // then : 결과 확인
        assertThat(myProfileResponse.getId()).isEqualTo(1L);
        assertThat(myProfileResponse.getEmail()).isEqualTo("kimjisu3268@gmail.com");
        assertThat(myProfileResponse.getNickname()).isEqualTo("jisu3268");
        assertThat(myProfileResponse.getAddress()).isEqualTo("Pangyo");
        assertThat(myProfileResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(myProfileResponse.getLastLoginAt()).isEqualTo(100L);
    }
}
