package com.jisu.backend.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Document(collection = "adViewHistory")
public class AdViewHistory {

  @Id
  private String id;

  private Long adId;

  private String username;

  private String clientIp;

  private Boolean isTrueView = false;

  private LocalDateTime createdDate = LocalDateTime.now();
}