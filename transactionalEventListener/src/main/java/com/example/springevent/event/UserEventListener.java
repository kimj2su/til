package com.example.springevent.event;

import com.example.springevent.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Slf4j
@Component
public class UserEventListener {

    @TransactionalEventListener
//    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void modifyUserStatus(UserEvent event) throws InterruptedException {
        log.info("요청 유저 상태: {}", event.getStatus());
        User user = event.getUser();
        log.info("수정전 유저 상태: {}", user.getStatus());
        user.modifyStatus(event.getStatus());
        log.info("수정된 유저 상태: {}", user.getStatus());
    }
}
