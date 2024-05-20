package com.jisu.testcodewitharchitecture.user.controller;

import com.jisu.testcodewitharchitecture.mock.TestContainer;
import com.jisu.testcodewitharchitecture.user.controller.response.UserResponse;
import com.jisu.testcodewitharchitecture.user.domain.UserCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserCreateControllerTest {

    @Test
    void 사용자는_회원_가입을_할_수있고_회원가입된_사용자는_PENDING_상태이다() throws Exception {
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(() -> "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        UserCreate userCreate = UserCreate.builder()
                .email("kimjisu3268@gmail.com")
                .nickname("kimjisu3268")
                .address("Pangyo2")
                .build();

        // when : 기능 수행
        ResponseEntity<UserResponse> result = testContainer.userCreateController.create(userCreate);

        // then : 결과 확인
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getLastLoginAt()).isNull();
        assertThat(result.getBody().getEmail()).isEqualTo("kimjisu3268@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("kimjisu3268");
        assertThat(testContainer.userRepository.getById(1).getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

}
