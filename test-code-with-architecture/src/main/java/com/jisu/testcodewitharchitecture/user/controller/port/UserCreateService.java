package com.jisu.testcodewitharchitecture.user.controller.port;

import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserCreate;

public interface UserCreateService {

    User create(UserCreate userCreate);
}
