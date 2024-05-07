package com.jisu.testcodewitharchitecture.post.service.port;

import com.jisu.testcodewitharchitecture.post.infrastructure.PostEntity;

import java.util.Optional;

public interface PostRepository {
    Optional<PostEntity> findById(long id);

    PostEntity save(PostEntity postEntity);
}
