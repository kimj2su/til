package com.jisu.testcodewitharchitecture.user.service;

import com.jisu.testcodewitharchitecture.common.domain.exception.CertificationCodeNotMatchedException;
import com.jisu.testcodewitharchitecture.common.domain.exception.ResourceNotFoundException;
import com.jisu.testcodewitharchitecture.mock.FakeMailSender;
import com.jisu.testcodewitharchitecture.mock.FakeUserRepository;
import com.jisu.testcodewitharchitecture.mock.TestClockHolder;
import com.jisu.testcodewitharchitecture.mock.TestUuidHolder;
import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserCreate;
import com.jisu.testcodewitharchitecture.user.domain.UserStatus;
import com.jisu.testcodewitharchitecture.user.domain.UserUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


public class UserServiceTest {

    private UserServiceImpl userService;

    @BeforeEach
    void init() {
        FakeMailSender fakeMailSender = new FakeMailSender();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.userService = UserServiceImpl.builder()
                .uuidHolder(new TestUuidHolder("T.T"))
                .clockHolder(new TestClockHolder(1678530673958L))
                .userRepository(fakeUserRepository)
                .certificationService(new CertificationService(fakeMailSender))
                .build();
        fakeUserRepository.save(User.builder()
                .id(1L)
                .email("kimjisu3268@gmail.com")
                .nickname("jisu3268")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build());
        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("kimjisu1234@gmail.com")
                .nickname("jisu1234")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build());
    }

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        // given
        String email = "kimjisu3268@gmail.com";

        // when
        User result = userService.getByEmail(email);

        // then
        assertThat(result.getNickname()).isEqualTo("jisu3268");
    }

    @Test
    void getByEmail은_PENDING_상태인_유저는_찾아올_수_없다() {
        // given
        String email = "kimjisu1234@gmail.com";

        // when
        // then
        assertThatThrownBy(() -> {
            User result = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById는_ACTIVE_상태인_유저를_찾아올_수_있다() {
        // given
        // when
        User result = userService.getById(1);

        // then
        assertThat(result.getNickname()).isEqualTo("jisu3268");
    }

    @Test
    void getById는_PENDING_상태인_유저는_찾아올_수_없다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            User result = userService.getById(2);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userCreateDto_를_이용하여_유저를_생성할_수_있다() {
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("kimjisu12345@gmail.com")
                .address("Seoul")
                .nickname("jisu1234")
                .build();

        // when
        User result = userService.create(userCreate);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
    }

    @Test
    void userUpdateDto_를_이용하여_유저를_수정할_수_있다() {
        // given
        UserUpdate userUpdate = UserUpdate.builder()
                .address("Incheon")
                .nickname("jisu3268")
                .build();

        // when
        userService.update(1, userUpdate);

        // then
        User user = userService.getById(1);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getAddress()).isEqualTo("Incheon");
        assertThat(user.getNickname()).isEqualTo("jisu3268");
    }

    @Test
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
        // given
        // when
        userService.login(1);

        // then
        User user = userService.getById(1);
        assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
        // given
        // when
        userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        // then
        User user = userService.getById(2);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }

}
