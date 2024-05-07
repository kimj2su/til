package com.jisu.testcodewitharchitecture.user.service.port;

import com.jisu.testcodewitharchitecture.user.domain.UserStatus;
import com.jisu.testcodewitharchitecture.user.infrastructure.UserEntity;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findById(long id);

    Optional<UserEntity> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);

    UserEntity save(UserEntity userEntity);

}
