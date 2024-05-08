package com.jisu.testcodewitharchitecture.post.service;

import com.jisu.testcodewitharchitecture.common.domain.exception.ResourceNotFoundException;
import com.jisu.testcodewitharchitecture.post.domain.Post;
import com.jisu.testcodewitharchitecture.post.domain.PostCreate;
import com.jisu.testcodewitharchitecture.post.domain.PostUpdate;
import com.jisu.testcodewitharchitecture.post.service.port.PostRepository;
import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public Post getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public Post create(PostCreate postCreate) {
        User user = userService.getById(postCreate.getWriterId());
        Post post = Post.from(user, postCreate);
        return postRepository.save(post);
    }

    public Post update(long id, PostUpdate postUpdate) {
        Post post = getById(id);
        post = post.update(postUpdate);
        return postRepository.save(post);
    }
}