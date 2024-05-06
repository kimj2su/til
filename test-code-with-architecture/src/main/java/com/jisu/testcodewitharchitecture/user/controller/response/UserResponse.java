package com.jisu.testcodewitharchitecture.user.controller.response;

import com.jisu.testcodewitharchitecture.user.domain.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String email;
    private String nickname;
    private UserStatus status;
    private Long lastLoginAt;
}
