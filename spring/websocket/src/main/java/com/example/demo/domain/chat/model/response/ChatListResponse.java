package com.example.demo.domain.chat.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;

import com.example.demo.domain.chat.model.Message;

@Schema(description = "Chatting List")
public record ChatListResponse(
    @Schema(description = "return Messagae : []")
    List<Message> result
) {} 
