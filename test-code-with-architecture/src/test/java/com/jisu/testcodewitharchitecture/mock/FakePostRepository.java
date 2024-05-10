package com.jisu.testcodewitharchitecture.mock;

import com.jisu.testcodewitharchitecture.post.domain.Post;
import com.jisu.testcodewitharchitecture.post.service.port.PostRepository;
import com.jisu.testcodewitharchitecture.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FakePostRepository implements PostRepository {

    private Long autoIncrementId = 0L;
    private final List<Post> data = new ArrayList<>();

    @Override
    public Optional<Post> findById(long id) {
        return data.stream()
                .filter(item -> Objects.equals(item.getId(), id))
                .findFirst();
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == null || post.getId() == 0) {
            Post newPost = Post.builder()
                    .id(++autoIncrementId)
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .writer(post.getWriter())
                    .build();
            data.add(newPost);
            return newPost;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), post.getId()));
            data.add(post);
            return post;
        }
    }
}
