package com.jisu.testcodewitharchitecture.user.controller;

import com.jisu.testcodewitharchitecture.user.controller.port.UserCreateService;
import com.jisu.testcodewitharchitecture.user.controller.response.UserResponse;
import com.jisu.testcodewitharchitecture.user.domain.User;
import com.jisu.testcodewitharchitecture.user.domain.UserCreate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저(users)")
@RestController
@RequestMapping("/api/users")
@Builder
@RequiredArgsConstructor
public class UserCreateController {

    private final UserCreateService userCreateService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreate userCreate) {
        User userEntity = userCreateService.create(userCreate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponse.from(userEntity));
    }

}