package com.jisu.testcodewitharchitecture.post.service;

import com.jisu.testcodewitharchitecture.common.domain.exception.ResourceNotFoundException;
import com.jisu.testcodewitharchitecture.post.domain.PostCreate;
import com.jisu.testcodewitharchitecture.post.domain.PostUpdate;
import com.jisu.testcodewitharchitecture.post.infrastructure.PostEntity;
import com.jisu.testcodewitharchitecture.post.service.port.PostRepository;
import com.jisu.testcodewitharchitecture.user.infrastructure.UserEntity;
import com.jisu.testcodewitharchitecture.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostEntity getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public PostEntity create(PostCreate postCreate) {
        UserEntity userEntity = userService.getById(postCreate.getWriterId());
        PostEntity postEntity = new PostEntity();
        postEntity.setWriter(userEntity);
        postEntity.setContent(postCreate.getContent());
        postEntity.setCreatedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }

    public PostEntity update(long id, PostUpdate postUpdate) {
        PostEntity postEntity = getById(id);
        postEntity.setContent(postUpdate.getContent());
        postEntity.setModifiedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }
}