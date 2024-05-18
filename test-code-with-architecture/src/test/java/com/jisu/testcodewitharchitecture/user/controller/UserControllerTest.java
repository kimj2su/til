package com.jisu.testcodewitharchitecture.user.controller;

import com.jisu.testcodewitharchitecture.common.domain.exception.CertificationCodeNotMatchedException;
import com.jisu.testcodewitharchitecture.common.domain.exception.ResourceNotFoundException;
import com.jisu.testcodewitharchitecture.common.service.ClockHolder;
import com.jisu.testcodewitharchitecture.mock.TestContainer;
import com.jisu.testcodewitharchitecture.user.controller.response.MyProfileResponse;
import com.jisu.testcodewitharchitecture.user.controller.response.UserResponse;
import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserStatus;
import com.jisu.testcodewitharchitecture.user.domain.UserUpdate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserControllerTest {

    @Test
    void 사용자는_특정_유저의_정보를_개인정보는_소거된채_전달_받을_수_있다() {
        // given : 선행조건 기술
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("kimjisu3268@gmail.com")
                .address("Pangyo")
                .nickname("jisu3268")
                .status(UserStatus.ACTIVE)
                .certificationCode("certificationCode")
                .build());
        // when : 기능 수행
        ResponseEntity<UserResponse> result = testContainer.userController.getUserById(1L);

        // then : 결과 확인
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("kimjisu3268@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("jisu3268");
    }

    @Test
    void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() {
        // given : 선행조건 기술
        TestContainer testContainer = TestContainer.builder().build();

        // when : 기능 수행 && then : 결과 확인
        assertThatThrownBy(() ->
                testContainer.userController.getUserById(123456789))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() {
        // given : 선행조건 기술
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("kimjisu3268@gmail.com")
                .address("Pangyo")
                .nickname("jisu3268")
                .status(UserStatus.ACTIVE)
                .certificationCode("certificationCode")
                .build());
        // when : 기능 수행
        ResponseEntity<Void> result = testContainer.userController.verifyEmail(1L, "certificationCode");

        // then : 결과 확인
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(302));
        Assertions.assertThat(testContainer.userRepository.findById(1L).get().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() {
        /// given : 선행조건 기술
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("kimjisu3268@gmail.com")
                .address("Pangyo")
                .nickname("jisu3268")
                .status(UserStatus.ACTIVE)
                .certificationCode("certificationCode")
                .build());

        // when : 기능 수행 && then : 결과 확인
        assertThatThrownBy(() -> testContainer.userController.verifyEmail(1L, "wrongCertificationCode"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }

    @Test
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() {
        // given : 선행조건 기술
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(new ClockHolder() {
                    @Override
                    public long millis() {
                        return 1678530673958L;
                    }
                })
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("kimjisu3268@gmail.com")
                .address("Pangyo")
                .nickname("jisu3268")
                .status(UserStatus.ACTIVE)
                .certificationCode("certificationCode")
                .build());

        // when : 기능 수행
        ResponseEntity<MyProfileResponse> result = testContainer.userController.getMyInfo("kimjisu3268@gmail.com");

        // then : 결과 확인
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("kimjisu3268@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("jisu3268");
        assertThat(result.getBody().getAddress()).isEqualTo("Pangyo");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_내_정보를_수정할_수_있다() throws Exception {
        // given : 선행조건 기술
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("kimjisu3268@gmail.com")
                .address("Pangyo")
                .nickname("jisu3268")
                .status(UserStatus.ACTIVE)
                .certificationCode("certificationCode")
                .build());
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("kimjisu3268")
                .address("Pangyo2")
                .build();

        // when : 기능 수행
        ResponseEntity<MyProfileResponse> result = testContainer.userController.updateMyInfo("kimjisu3268@gmail.com", userUpdate);

        // then : 결과 확인
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("kimjisu3268@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("kimjisu3268");
    }

}
