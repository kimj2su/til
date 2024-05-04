package com.jisu.testcodewitharchitecture.service;

import com.jisu.testcodewitharchitecture.exception.ResourceNotFoundException;
import com.jisu.testcodewitharchitecture.model.dto.PostCreateDto;
import com.jisu.testcodewitharchitecture.model.dto.PostUpdateDto;
import com.jisu.testcodewitharchitecture.repository.PostEntity;
import com.jisu.testcodewitharchitecture.repository.PostRepository;
import com.jisu.testcodewitharchitecture.repository.UserEntity;
import java.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostEntity getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public PostEntity create(PostCreateDto postCreateDto) {
        UserEntity userEntity = userService.getById(postCreateDto.getWriterId());
        PostEntity postEntity = new PostEntity();
        postEntity.setWriter(userEntity);
        postEntity.setContent(postCreateDto.getContent());
        postEntity.setCreatedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }

    public PostEntity update(long id, PostUpdateDto postUpdateDto) {
        PostEntity postEntity = getById(id);
        postEntity.setContent(postUpdateDto.getContent());
        postEntity.setModifiedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }
}