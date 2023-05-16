package com.example.springevent.event;

import com.example.springevent.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.springevent.domain.user.UserStatus.*;
import static org.junit.jupiter.api.Assertions.*;

class UserEventTest {

    @DisplayName("유저 상태를 ACTIVE로 생성한다.")
    @Test
    void createUserEvent() {
        User user = User.builder()
                .userName("홍길동")
                .status(INACTIVE)
                .build();
        UserEvent userEvent = new UserEvent(ACTIVE, user);
        assertEquals(userEvent.getStatus(), ACTIVE);
    }

    @DisplayName("유저 상태를 INACTIVE로 생성한다.")
    @Test
    void createUserEvent2() {
        User user = User.builder()
                .userName("홍길동")
                .status(INACTIVE)
                .build();
        UserEvent userEvent = new UserEvent(INACTIVE, user);
        assertEquals(userEvent.getStatus(), INACTIVE);
    }

}