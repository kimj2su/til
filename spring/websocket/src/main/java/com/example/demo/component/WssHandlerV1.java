package com.example.demo.component;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.demo.domain.chat.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

// public class Test {

// }

@Component
@RequiredArgsConstructor
public class WssHandlerV1 extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage msg) {
        try {
            String payload = msg.getPayload();
            Message message = objectMapper.readValue(payload, Message.class);

            // 1. DB에 있는 데이터 인지 [from , to]
            // 2. 채팅 메시지 데이터 저장

            session.sendMessage(new TextMessage(payload));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
