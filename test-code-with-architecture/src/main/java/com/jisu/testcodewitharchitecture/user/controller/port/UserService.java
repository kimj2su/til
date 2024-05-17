package com.jisu.testcodewitharchitecture.user.controller.port;

import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserCreate;
import com.jisu.testcodewitharchitecture.user.domain.UserUpdate;

public interface UserService {

    User getByEmail(String email);

    User getById(long id);

    User create(UserCreate userCreate);

    User update(long id, UserUpdate userUpdate);

    void login(long id);

    void verifyEmail(long id, String certificationCode);
}
