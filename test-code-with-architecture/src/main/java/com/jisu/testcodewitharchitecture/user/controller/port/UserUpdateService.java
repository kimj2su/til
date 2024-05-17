package com.jisu.testcodewitharchitecture.user.controller.port;

import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserUpdate;

public interface UserUpdateService {

    User update(long id, UserUpdate userUpdate);
}
