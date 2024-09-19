package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WssConfig implements WebSocketMessageBrokerConfigurer {

  // pub/sub 메시지를 처리할 endpoint 설정
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/sub");
    registry.setApplicationDestinationPrefixes("/pub");
  }

  // 연결되는 base endpoint 설정
  @Override
  public void registerStompEndpoints(StompEndpointRegistry register) {
    register.addEndpoint("/ws-stomp")
        .setAllowedOrigins("*");
    // .withSockJS();
  }

//     @Override
//     public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//         registry.addHandler(null, "/ws/v1/chat")
//             .setAllowedOrigins("*");
//     }


}
