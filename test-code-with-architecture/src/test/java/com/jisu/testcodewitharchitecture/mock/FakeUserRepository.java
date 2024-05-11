package com.jisu.testcodewitharchitecture.mock;

import com.jisu.testcodewitharchitecture.common.domain.exception.ResourceNotFoundException;
import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserStatus;
import com.jisu.testcodewitharchitecture.user.service.port.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeUserRepository implements UserRepository {

    // 소형 테스트는 단일 스레드에서 돌아가기 때문에 동기화 처리를 하지 않아도 된다. long, arraylist를 사용해도 됨
    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<User> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public User getById(long writerId) {
        return findById(writerId).orElseThrow(() -> new ResourceNotFoundException("Users", writerId));
    }

    @Override
    public Optional<User> findById(long id) {
        return data.stream()
                .filter(item -> Objects.equals(item.getId(), id))
                .findFirst();
    }

    @Override
    public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
        return data.stream()
                .filter(item -> Objects.equals(item.getId(), id) && item.getStatus() == userStatus)
                .findFirst();
    }

    @Override
    public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
        return data.stream()
                .filter(item -> Objects.equals(item.getEmail(), email) && item.getStatus() == userStatus)
                .findFirst();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null || user.getId() == 0) {
            User newUser = User.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .status(user.getStatus())
                    .lastLoginAt(user.getLastLoginAt())
                    .build();
            data.add(newUser);
            return newUser;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), user.getId()));
            data.add(user);
            return user;
        }
    }
}