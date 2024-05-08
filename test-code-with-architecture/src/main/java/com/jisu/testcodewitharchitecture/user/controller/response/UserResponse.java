package com.jisu.testcodewitharchitecture.user.controller.response;

import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserResponse {

    private final Long id;
    private final String email;
    private final String nickname;
    private final UserStatus status;
    private final Long lastLoginAt;

    @Builder
    public UserResponse(Long id, String email, String nickname, UserStatus status, Long lastLoginAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.status = status;
        this.lastLoginAt = lastLoginAt;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .status(user.getStatus())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
