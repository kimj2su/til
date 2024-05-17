package com.jisu.testcodewitharchitecture.user.controller.port;

import com.jisu.testcodewitharchitecture.user.domain.User;

public interface UserReadService {

    User getByEmail(String email);

    User getById(long id);

}
