package com.jisu.testcodewitharchitecture.user.domain;

import com.jisu.testcodewitharchitecture.common.domain.exception.CertificationCodeNotMatchedException;
import com.jisu.testcodewitharchitecture.mock.TestClockHolder;
import com.jisu.testcodewitharchitecture.mock.TestUuidHolder;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserTest {

    @Test
    void User는_UserCreate_객체로_생성할_수_있다() {
        // given : 선행조건 기술
        UserCreate userCreate = UserCreate.builder()
                .email("kimjisu3268@gmail.com")
                .nickname("jisu3268")
                .address("Pangyo")
                .build();

        // when : 기능 수행
        User user = User.from(userCreate, new TestUuidHolder("abc1234"));

        // then : 결과 확인
        assertThat(user.getEmail()).isEqualTo("kimjisu3268@gmail.com");
        assertThat(user.getNickname()).isEqualTo("jisu3268");
        assertThat(user.getAddress()).isEqualTo("Pangyo");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("abc1234");
    }

    @Test
    void User는_UserUpdate_객체로_수정할_수_있다() {
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
        UserUpdate userUpdate = UserUpdate.builder()
                .address("Seoul")
                .nickname("jisu1234")
                .build();

        // when : 기능 수행
        user =  user.update(userUpdate);

        // then : 결과 확인
        assertThat(user.getEmail()).isEqualTo("kimjisu3268@gmail.com");
        assertThat(user.getNickname()).isEqualTo("jisu1234");
        assertThat(user.getAddress()).isEqualTo("Seoul");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isNotNull();
    }

    @Test
    void User는_로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() {
        // given : 선행조건 기술
        User user = User.builder()
                .id(1L)
                .email("kimjisu3268@gmail.com")
                .nickname("jisu3268")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        // when : 기능 수행
        user = user.login(new TestClockHolder(1678530673958L));

        // then : 결과 확인
        assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    void User는_인증_코드로_계정을_활성화_할_수_있다() {
        // given : 선행조건 기술
        User user = User.builder()
                .id(1L)
                .email("kimjisu3268@gmail.com")
                .nickname("jisu3268")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        // when : 기능 수행
        user = user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        // then : 결과 확인
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void User는_잘못된_인증_코드로_계정을_활성화_하려면_에러를_던진다() {
        // given : 선행조건 기술
        User user = User.builder()
                .id(1L)
                .email("kimjisu3268@gmail.com")
                .nickname("jisu3268")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        // when : 기능 수행 & then : 결과 확인
        assertThatThrownBy(() -> user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}
