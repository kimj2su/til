package com.example.springevent.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.RecordApplicationEvents;

import static com.example.springevent.domain.user.UserStatus.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RecordApplicationEvents
class UserServiceTest {
    @Autowired
    private UserService userService;

    @DisplayName("유저를 생성한다.")
    @Test
    void createUser() {
        String username = "홍길동";
        User user = userService.createUser(username);
        assertThat(user.getUserName()).isEqualTo(username);
        assertThat(user.getStatus()).isEqualTo(INACTIVE);
    }

    @DisplayName("트랜잭션 어노테이션과 이벤트를 발행하여 유저 상태를 ACTIVE로 변경한다.")
    @Test
    void modifyUserStatusWithTransactional() {
        String username = "홍길동";
        User user = userService.createUser(username);
        assertThat(user.getUserName()).isEqualTo(username);
        assertThat(user.getStatus()).isEqualTo(INACTIVE);

        //@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, AFTER_COMPLETION, AFTER_COMMIT)
        User modifiedUser = userService.modifyUserStatusWithTransactional(user.getId(), ACTIVE, false);
        assertThat(modifiedUser.getUserName()).isEqualTo(username);
        assertThat(modifiedUser.getStatus()).isEqualTo(ACTIVE);
    }

    @DisplayName("예외가 발생하면 이벤트가 발생하지 않는다.")
    @Test
    void modifyUserStatusWithTransactionalThrowsException() {
        String username = "홍길동";
        User user = userService.createUser(username);
        assertThat(user.getUserName()).isEqualTo(username);
        assertThat(user.getStatus()).isEqualTo(INACTIVE);

        // @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
        assertThatThrownBy(() -> userService.modifyUserStatusWithTransactional(user.getId(), ACTIVE, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("예외 발생");
    }

    @DisplayName("트랜잭션 어노테이션없이 이벤트를 사용하면 이벤트가 발행되지 않아 유저 상태가 변하지 않는다.")
    @Test
    void modifyUserStatusWithOutTransactional() {
        String username = "홍길동";
        User user = userService.createUser(username);
        assertThat(user.getUserName()).isEqualTo(username);
        assertThat(user.getStatus()).isEqualTo(INACTIVE);

        User modifiedUser = userService.modifyUserStatusWithOutTransactional(user.getId(), ACTIVE);
        assertThat(modifiedUser.getUserName()).isEqualTo(username);
        assertThat(modifiedUser.getStatus()).isEqualTo(INACTIVE);
    }

}