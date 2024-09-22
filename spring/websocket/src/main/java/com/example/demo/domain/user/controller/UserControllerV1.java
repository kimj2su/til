package com.example.demo.domain.user.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.auth.model.request.CreateUserRequest;
import com.example.demo.domain.auth.model.request.LoginRequest;
import com.example.demo.domain.auth.model.response.CreateUserResponse;
import com.example.demo.domain.auth.model.response.LoginResponse;
import com.example.demo.domain.auth.service.AuthService;
import com.example.demo.domain.user.model.response.UserSearchResponse;
import com.example.demo.domain.user.service.UserServiceV1;
import com.example.demo.security.JWTProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "User API", description = "V1 User API")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor    
public class UserControllerV1 {

    private final UserServiceV1 userServiceV1;


    @Operation(
        summary = "User Name List Search",
        description = "User Name을 기반으로 Like 검색 실행"
    )
    @GetMapping("/search/{name}")
    public UserSearchResponse searchUser(
        @PathVariable("name") String name,
        @RequestHeader("Authorization") String authString
    ) {
        String token = JWTProvider.extractToken(authString);
        String user = JWTProvider.getUserFromToken(token);

        return userServiceV1.searchUser(name, user);
    }
}
