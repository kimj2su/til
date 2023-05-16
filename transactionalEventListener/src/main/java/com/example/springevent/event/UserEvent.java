package com.example.springevent.event;

import com.example.springevent.domain.user.User;
import com.example.springevent.domain.user.UserStatus;
import lombok.Getter;

@Getter
public class UserEvent {

    private UserStatus status;
    private User user;

    public UserEvent(UserStatus status, User user) {
        this.status = status;
        this.user = user;
    }
}
