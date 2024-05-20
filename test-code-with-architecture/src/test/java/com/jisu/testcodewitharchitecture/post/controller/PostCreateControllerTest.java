package com.jisu.testcodewitharchitecture.post.controller;

import com.jisu.testcodewitharchitecture.mock.TestContainer;
import com.jisu.testcodewitharchitecture.post.controller.response.PostResponse;
import com.jisu.testcodewitharchitecture.post.domain.PostCreate;
import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PostCreateControllerTest {

    @Test
    void 사용자는_게시물을_작성할_수_있다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1678530673958L)
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("kimjisu3268@gmail.com")
                .nickname("kimjisu3268")
                .address("Pangyo")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(100L)
                .build());
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("helloworld")
                .build();

        // when : 기능 수행
        ResponseEntity<PostResponse> result = testContainer.postCreateController.create(postCreate);

        // then : 결과 확인
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().createdAt()).isEqualTo(1678530673958L);
        assertThat(result.getBody().content()).isEqualTo("helloworld");
        assertThat(result.getBody().writer().getNickname()).isEqualTo("kimjisu3268");
        assertThat(testContainer.userRepository.getById(1).getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }
}
