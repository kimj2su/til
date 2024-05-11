package com.jisu.testcodewitharchitecture.post.domain;

import com.jisu.testcodewitharchitecture.mock.TestClockHolder;
import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {

    @Test
    void PostCreate으로_게시물을_만들_수_있다() {
        // given : 선행조건 기술
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("HelloWorld")
                .build();
        User user = User.builder()
                .id(1L)
                .email("kimjisu3268@gmail.com")
                .nickname("jisu3268")
                .address("Pangyo")
                .status(UserStatus.ACTIVE)
                .certificationCode(UUID.randomUUID().toString())
                .build();
        TestClockHolder testClockHolder = new TestClockHolder(1678530673958L);

        // when : 기능 수행
        Post post = Post.from(user, postCreate, testClockHolder);

        // then : 결과 확인
        assertThat(post.getContent()).isEqualTo("HelloWorld");
        assertThat(post.getCreatedAt()).isEqualTo(1678530673958L);
        assertThat(post.getWriter().getId()).isEqualTo(1L);
        assertThat(post.getWriter().getEmail()).isEqualTo("kimjisu3268@gmail.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("jisu3268");
        assertThat(post.getWriter().getAddress()).isEqualTo("Pangyo");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isNotNull();
    }

    @Test
    void PostUpdate으로_게시물을_수정할_수_있다() {
        // given : 선행조건 기술
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("HelloWorld")
                .build();
        User user = User.builder()
                .id(1L)
                .email("kimjisu3268@gmail.com")
                .nickname("jisu3268")
                .address("Pangyo")
                .status(UserStatus.ACTIVE)
                .certificationCode(UUID.randomUUID().toString())
                .build();
        TestClockHolder testClockHolder = new TestClockHolder(1678530673958L);
        Post post = Post.from(user, postCreate, testClockHolder);
        PostUpdate postUpdate = PostUpdate.builder()
                .content("foobar")
                .build();

        // when : 기능 수행
        Post update = post.update(postUpdate, testClockHolder);

        // then : 결과 확인
        assertThat(update.getContent()).isEqualTo("foobar");
        assertThat(update.getModifiedAt()).isEqualTo(1678530673958L);
    }
}
