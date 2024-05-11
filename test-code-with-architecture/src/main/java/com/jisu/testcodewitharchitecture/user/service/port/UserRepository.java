package com.jisu.testcodewitharchitecture.user.service.port;

import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserStatus;

import java.util.Optional;

public interface UserRepository {

    User getById(long writerId);

    Optional<User> findById(long id);

    Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

    User save(User user);
}
