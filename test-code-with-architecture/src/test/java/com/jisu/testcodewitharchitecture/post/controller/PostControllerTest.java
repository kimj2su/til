package com.jisu.testcodewitharchitecture.post.controller;

import com.jisu.testcodewitharchitecture.common.domain.exception.ResourceNotFoundException;
import com.jisu.testcodewitharchitecture.mock.TestContainer;
import com.jisu.testcodewitharchitecture.post.controller.response.PostResponse;
import com.jisu.testcodewitharchitecture.post.domain.Post;
import com.jisu.testcodewitharchitecture.post.domain.PostUpdate;
import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PostControllerTest {

    @Test
    void 사용자는_게시물을_단건_조회_할_수_있다() throws Exception {
        // given : 선행조건 기술
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(() -> "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
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
        testContainer.postRepository.save(Post.builder()
                .id(1L)
                .writer(testContainer.userRepository.getById(1))
                .content("hello")
                .createdAt(100L)
                .build());

        // when : 기능 수행
        ResponseEntity<PostResponse> result = testContainer.postController.getPostById(1L);

        // then : 결과 확인
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().content()).isEqualTo("hello");
        assertThat(result.getBody().writer().getNickname()).isEqualTo("kimjisu3268");
        assertThat(result.getBody().createdAt()).isEqualTo(100L);
    }

    @Test
    void 사용자가_존재하지_않는_게시물을_조회할_경우_에러가_난다() throws Exception {
        // given : 선행조건 기술
        TestContainer testContainer = TestContainer.builder().build();

        // when : 기능 수행 && then : 결과 확인
        assertThatThrownBy(() ->
                testContainer.postController.getPostById(1))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_게시물을_수정할_수_있다() throws Exception {
        // given : 선행조건 기술
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
        testContainer.postRepository.save(Post.builder()
                .id(1L)
                .writer(testContainer.userRepository.getById(1))
                .content("hello")
                .createdAt(100L)
                .build());

        // when : 기능 수행
        ResponseEntity<PostResponse> result = testContainer.postController.updatePost(1L, PostUpdate.builder()
                .content("foobar")
                .build());

        // then : 결과 확인
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().content()).isEqualTo("foobar");
        assertThat(result.getBody().writer().getNickname()).isEqualTo("kimjisu3268");
        assertThat(result.getBody().createdAt()).isEqualTo(100L);
    }
}
