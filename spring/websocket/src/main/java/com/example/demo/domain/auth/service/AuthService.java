package com.example.demo.domain.auth.service;

import com.example.demo.common.exception.CustomException;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.domain.auth.model.request.CreateUserRequest;
import com.example.demo.domain.auth.model.request.LoginRequest;
import com.example.demo.domain.auth.model.response.CreateUserResponse;
import com.example.demo.domain.auth.model.response.LoginResponse;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.repository.entity.User;
import com.example.demo.domain.repository.entity.UserCredentials;
import com.example.demo.security.Hasher;
import com.example.demo.security.JWTProvider;
import java.sql.Timestamp;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final Hasher hasher;

  @Transactional(transactionManager = "createChatTransactionManager")
  public CreateUserResponse createUser(CreateUserRequest request) {
    Optional<User> user = userRepository.findByName(request.name());

    System.out.println(request.name());
    System.out.println(request.password());

    if (user.isPresent()) {
      log.error("USER_ALREADY_EXISTS: {}", request.name());
      throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
    }

    try {

      User newUser = this.newUser(request.name());

      UserCredentials newCredentials = this.newUserCredentials(request.password(), newUser);
      newUser.setCredentials(newCredentials);

      User savedUser = userRepository.save(newUser);

      if (savedUser == null) {
        System.out.println("------------");
        throw new CustomException(ErrorCode.USER_SAVED_FAILED);
      }

    } catch (Exception e) {
      throw new CustomException(ErrorCode.USER_SAVED_FAILED, e.getMessage());
    }

    return new CreateUserResponse(request.name());
  }

  public LoginResponse login(LoginRequest request) {
    Optional<User> user = userRepository.findByName(request.name());

    if (!user.isPresent()) {
      log.error("NOT_EXIST_USER: {}", request.name());
      throw new CustomException(ErrorCode.NOT_EXIST_USER);
    }

    user.map(u -> {
      String hashedValue = hasher.getHashingValue(request.password());

      if (!u.getUserCredentials().getHashed_password().equals(hashedValue)) {
        throw new CustomException(ErrorCode.MIS_MATCH_PASSWORD);
      }

      return hashedValue;
    }).orElseThrow(() -> {
      throw new CustomException(ErrorCode.MIS_MATCH_PASSWORD);
    });

    String token = JWTProvider.createRefreshToken(request.name());
    return new LoginResponse(ErrorCode.SUCCESS, token);
  }

  public String getUserFromToken(String token) {
    return JWTProvider.getUserFromToken(token);
  }

  private User newUser(String name) {
    User newUser = User.builder()
        .name(name)
        .created_at(new Timestamp(System.currentTimeMillis()))
        .build();

    return newUser;
  }

  private UserCredentials newUserCredentials(String password, User user) {
    String hashedValue = hasher.getHashingValue(password);

    UserCredentials cre = UserCredentials.
        builder().
        user(user).
        hashed_password(hashedValue).
        build();

    return cre;
  }
}
