package com.jisu.testcodewitharchitecture.post.controller.response;

import com.jisu.testcodewitharchitecture.post.domain.Post;
import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostResponseTest {
    @Test
    void Post로_응답을_생성할_수_있다() {
        // given : 선행조건 기술
        Post post = Post.builder()
                .id(1L)
                .content("HelloWorld")
                .modifiedAt(16L)
                .createdAt(1L)
                .writer(User.builder()
                        .id(1L)
                        .email("kimjisu3268@gmail.com")
                        .address("Pangyo")
                        .nickname("jisu3268")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("certificationCode")
                        .build())
                .build();

        // when : 기능 수행
        PostResponse postResponse = PostResponse.from(post);

        // then : 결과 확인
        assertThat(postResponse.content()).isEqualTo("HelloWorld");
        assertThat(postResponse.writer().getId()).isEqualTo(1L);
        assertThat(postResponse.writer().getEmail()).isEqualTo("kimjisu3268@gmail.com");
        assertThat(postResponse.writer().getNickname()).isEqualTo("jisu3268");
        assertThat(postResponse.writer().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}
