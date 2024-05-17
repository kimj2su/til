package com.jisu.testcodewitharchitecture.post.service;

import com.jisu.testcodewitharchitecture.mock.FakePostRepository;
import com.jisu.testcodewitharchitecture.mock.FakeUserRepository;
import com.jisu.testcodewitharchitecture.mock.TestClockHolder;
import com.jisu.testcodewitharchitecture.post.domain.Post;
import com.jisu.testcodewitharchitecture.post.domain.PostCreate;
import com.jisu.testcodewitharchitecture.post.domain.PostUpdate;
import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class PostServiceTest {

    private PostServiceImpl postService;

    @BeforeEach
    void init() {
        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        postService = PostServiceImpl.builder()
                .postRepository(fakePostRepository)
                .userRepository(fakeUserRepository)
                .clockHolder(new TestClockHolder(1678530673958L))
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
        fakePostRepository.save(Post.builder()
                .id(1L)
                .content("helloworld")
                .writer(fakeUserRepository.getById(1))
                .createdAt(1678530673958L)
                .modifiedAt(0L)
                .build());
    }

    @Test
    void getById는_존재하는_게시물을_내려준다() {
        // given
        // when
        Post result = postService.getById(1);

        // then
        assertThat(result.getContent()).isEqualTo("helloworld");
        assertThat(result.getWriter().getEmail()).isEqualTo("kimjisu3268@gmail.com");
    }

    @Test
    void postCreateDto_를_이용하여_게시물을_생성할_수_있다() {
        // given
        PostCreate postCreate = PostCreate.builder()
            .writerId(1)
            .content("foobar")
            .build();

        // when
        Post result = postService.create(postCreate);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("foobar");
        assertThat(result.getCreatedAt()).isEqualTo(1678530673958L);
    }

    @Test
    void postUpdateDto_를_이용하여_게시물을_수정할_수_있다() {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
            .content("hello world :)")
            .build();

        // when
        postService.update(1, postUpdate);

        // then
        Post post= postService.getById(1);
        assertThat(post.getContent()).isEqualTo("hello world :)");
        assertThat(post.getModifiedAt()).isEqualTo(1678530673958L);
    }

}
