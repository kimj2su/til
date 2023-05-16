package com.example.springevent.event;

import com.example.springevent.domain.user.User;
import com.example.springevent.domain.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import static com.example.springevent.domain.user.UserStatus.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RecordApplicationEvents
class UserEventListenerTest {

    @Autowired
    private UserEventListener userEventListener;

    @Autowired
    private UserService userService;

    @Autowired
    ApplicationEvents events;

    @Test
    void modifyUserStatus() throws InterruptedException {
        User user = userService.createUser("홍길동");
        User modifyUser = userService.modifyUserStatusWithTransactional(user.getId(), ACTIVE, false);

        assertThat(user.getStatus()).isEqualTo(INACTIVE);
        assertThat(modifyUser.getStatus()).isEqualTo(ACTIVE);
    }
}
