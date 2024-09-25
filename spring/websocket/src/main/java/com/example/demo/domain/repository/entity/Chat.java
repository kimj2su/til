package com.example.demo.domain.repository.entity;

import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(value = "chat")
public class Chat {

  @Id
  private Long TID;

  private String sender;

  private String receiver;

  private String message;

  private Timestamp created_at;

}
