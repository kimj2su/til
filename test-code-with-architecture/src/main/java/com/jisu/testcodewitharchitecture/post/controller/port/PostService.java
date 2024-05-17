package com.jisu.testcodewitharchitecture.post.controller.port;

import com.jisu.testcodewitharchitecture.post.domain.Post;
import com.jisu.testcodewitharchitecture.post.domain.PostCreate;
import com.jisu.testcodewitharchitecture.post.domain.PostUpdate;

public interface PostService {

    Post getById(long id);

    Post create(PostCreate postCreate);

    Post update(long id, PostUpdate postUpdate);
}
