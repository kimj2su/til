package com.jisu.testcodewitharchitecture.post.controller.response;

import com.jisu.testcodewitharchitecture.post.domain.Post;
import com.jisu.testcodewitharchitecture.user.controller.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

public record PostResponse(Long id, String content, Long createdAt, Long modifiedAt, UserResponse writer) {

    @Builder
    public PostResponse {
    }

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .writer(UserResponse.from(post.getWriter()))
                .build();
    }
}
