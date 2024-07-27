package com.jisu.backend.service;

import com.jisu.backend.entity.JwtBlackList;
import com.jisu.backend.jwt.JwtUtil;
import com.jisu.backend.repository.JwtBlackListRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListService {

  private final JwtBlackListRepository jwtBlackListRepository;
  private final JwtUtil jwtUtil;

  public void blackListToken(String token, LocalDateTime expirationTime, String username) {
    jwtBlackListRepository.save(JwtBlackList.builder()
        .token(token)
        .expirationTime(expirationTime)
        .username(username)
        .build());
  }

  public boolean isTokenInBlackList(String token, String username) {
    Optional<JwtBlackList> blackListToken = jwtBlackListRepository.findTop1ByUsernameOrderByExpirationTimeDesc(
        username);
    if (blackListToken.isEmpty()) {
      return false;
    }

    LocalDateTime currentTokenExpirationTime = jwtUtil.getExpirationDateFromToken(token).toInstant()
        .atZone(ZoneId.systemDefault()).toLocalDateTime();
    return blackListToken.get().getExpirationTime()
        .isAfter(currentTokenExpirationTime.minusMinutes(60));
  }
}
