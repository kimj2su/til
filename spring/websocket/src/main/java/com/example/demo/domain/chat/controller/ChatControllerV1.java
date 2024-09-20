package com.example.demo.domain.chat.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.auth.model.request.CreateUserRequest;
import com.example.demo.domain.auth.model.request.LoginRequest;
import com.example.demo.domain.auth.model.response.CreateUserResponse;
import com.example.demo.domain.auth.model.response.LoginResponse;
import com.example.demo.domain.auth.service.AuthService;
import com.example.demo.domain.chat.model.response.ChatListResponse;
import com.example.demo.domain.chat.service.ChatServiceV1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Chata API", description = "V1 Chat API")
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor    
public class ChatControllerV1 {
    
    private final ChatServiceV1 chatServiceV1;

    @Operation(
        summary = "채팅 리스트를 가져옵니다.",
        description = "가장 최근 10개의 채팅 리스트를 가져옵니다."
    )
    @GetMapping("/chat-list")
    public ChatListResponse chatList(
        @RequestParam("name") @Valid String to,
        @RequestParam("from") @Valid String from
    ) {
        return chatServiceV1.chatList(from, to);
    }
}
