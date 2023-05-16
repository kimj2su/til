package com.example.springevent.domain.user;

import com.example.springevent.event.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.springevent.domain.user.UserStatus.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final ApplicationEventPublisher publisher;
    private final UserRepository userRepository;
    public User createUser(String userName) {

        User user = User.builder()
                .userName(userName)
                .status(INACTIVE)
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public User modifyUserStatusWithTransactional(Long id, UserStatus status, Boolean isException) {
        log.info("메서드 시작");
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + id));

        publisher.publishEvent(new UserEvent(status, user));
        log.info("이벤트 발생 요청");

        if (isException) {
            throw new IllegalArgumentException("예외 발생");
        }

        log.info("메서드 종료");
        return user;
    }

    public User modifyUserStatusWithOutTransactional(Long id, UserStatus status) {
        log.info("메서드 시작");
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + id));

        publisher.publishEvent(new UserEvent(status, user));
        log.info("이벤트 발생 요청");

        log.info("메서드 종료");
        return user;
    }
}
