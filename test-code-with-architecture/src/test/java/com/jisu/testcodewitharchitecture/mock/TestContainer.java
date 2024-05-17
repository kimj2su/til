package com.jisu.testcodewitharchitecture.mock;

import com.jisu.testcodewitharchitecture.common.service.ClockHolder;
import com.jisu.testcodewitharchitecture.common.service.UuidHolder;
import com.jisu.testcodewitharchitecture.post.controller.port.PostService;
import com.jisu.testcodewitharchitecture.post.service.PostServiceImpl;
import com.jisu.testcodewitharchitecture.post.service.port.PostRepository;
import com.jisu.testcodewitharchitecture.user.controller.port.AuthenticationService;
import com.jisu.testcodewitharchitecture.user.controller.port.UserCreateService;
import com.jisu.testcodewitharchitecture.user.controller.port.UserReadService;
import com.jisu.testcodewitharchitecture.user.controller.port.UserUpdateService;
import com.jisu.testcodewitharchitecture.user.service.CertificationService;
import com.jisu.testcodewitharchitecture.user.service.UserServiceImpl;
import com.jisu.testcodewitharchitecture.user.service.port.MailSender;
import com.jisu.testcodewitharchitecture.user.service.port.UserRepository;
import lombok.Builder;

public class TestContainer {

    public final MailSender mailSender;
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public final UserReadService userReadService;
    public final UserCreateService userCreateService;
    public final UserUpdateService userUpdateService;
    public final AuthenticationService authenticationService;
    public final PostService postService;
    public final CertificationService certificationService;

    @Builder
    public TestContainer(ClockHolder clockHolder, UuidHolder uuidHolder) {
        this.mailSender = new FakeMailSender();
        this.userRepository = new FakeUserRepository();
        this.postRepository = new FakePostRepository();
        this.postService = PostServiceImpl.builder()
                .postRepository(this.postRepository)
                .userRepository(this.userRepository)
                .clockHolder(clockHolder)
                .build();
        this.certificationService = new CertificationService(this.mailSender);
        UserServiceImpl userService = UserServiceImpl.builder()
                .uuidHolder(uuidHolder)
                .clockHolder(clockHolder)
                .userRepository(this.userRepository)
                .certificationService(this.certificationService)
                .build();
        this.userReadService = userService;
        this.userCreateService = userService;
        this.userUpdateService = userService;
        this.authenticationService = userService;
    }
}
