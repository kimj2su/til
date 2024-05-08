package com.jisu.testcodewitharchitecture.post.service.port;

import com.jisu.testcodewitharchitecture.post.domain.Post;

import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(long id);

    Post save(Post post);
}
