package com.example.demo.domain.chat.service;

import com.example.demo.domain.chat.model.Message;
import com.example.demo.domain.chat.model.response.ChatListResponse;
import com.example.demo.domain.repository.ChatRepository;
import com.example.demo.domain.repository.entity.Chat;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatServiceV1 {

  private final ChatRepository chatRepository;

  public ChatListResponse chatList(String from, String to) {
    List<Chat> chats = chatRepository.findTop10BySenderOrReceiverOrderByTIDDesc(from, to);

    // Entity -> DTO
    List<Message> res = chats.stream()
        .map(chat -> new Message(chat.getReceiver(), chat.getSender(), chat.getMessage()))
        .collect(Collectors.toList());

    return new ChatListResponse(res);
  }

  //  @Transactional(transactionManager = "createChatTransactionManager")
  public void saveChatMessage(Message msg) {
    Chat chat = Chat.builder().
        sender(msg.getFrom()).
        receiver(msg.getTo()).
        message(msg.getMessage()).
        created_at(new Timestamp(System.currentTimeMillis())).
        build();

    chatRepository.save(chat);
  }
}
