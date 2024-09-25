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
@Document(value = "user")
public class User {

  @Id
  private Long t_id;

  private String name;

  private Timestamp created_at;

  private UserCredentials userCredentials;

  public void setCredentials(UserCredentials credentials) {
    this.userCredentials = credentials;
  }
}
