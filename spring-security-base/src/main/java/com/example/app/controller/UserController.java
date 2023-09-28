package com.example.app.controller;

import com.example.app.controller.request.UserJoinRequest;
import com.example.app.controller.request.UserLoginRequest;
import com.example.app.controller.response.Response;
import com.example.app.controller.response.UserLoginResponse;
import com.example.app.dto.UserDto;
import com.example.app.dto.response.UserJoinResponse;
import com.example.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        UserDto userDto = userService.join(request.getName(), request.getPassword());
        System.out.println("user: " + userDto);
        return Response.success(UserJoinResponse.fromUser(userDto));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getName(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }

    @GetMapping("")
    public String user(@AuthenticationPrincipal UserDto principalDetails) {
        System.out.println("authentication = " + principalDetails);
        System.out.println("authentication = " + principalDetails.getUsername());
        return principalDetails.getUsername();
    }

    @GetMapping("/test")
    public String user2(@AuthenticationPrincipal UserDto principalDetails) {
        System.out.println("authentication = " + principalDetails);
        System.out.println("authentication = " + principalDetails.getUsername());
        return principalDetails.getUsername();
    }
}
