package com.example.app.service;

import com.example.app.dto.UserDto;
import com.example.app.exception.ErrorCode;
import com.example.app.exception.SampleApplicationException;
import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import com.example.app.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public UserDto loadUserByName(String userName) {
//        return userCacheRepository.getUser(userName).orElseGet(() ->
//                userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(() ->
//                        new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s no founded", userName))));

        return  userRepository.findByUserName(userName).map(UserDto::fromEntity).orElseThrow(() ->
                        new SampleApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s no founded", userName)));
    }

    @Transactional
    public UserDto join(String username, String password) {
        userRepository.findByUserName(username).ifPresent(it -> {
            throw new SampleApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", username));
        });

        User userEntity = userRepository.save(User.of(username, encoder.encode(password)));
        return UserDto.fromEntity(userEntity);
    }

    public String login(String username, String password) {
//        UserEntity userEntity = userEntityRepository.findByUserName(username).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", username)));
        UserDto userDto = loadUserByName(username);
//        userCacheRepository.setUser(user);

        if (!encoder.matches(password, userDto.getPassword())) {
            throw new SampleApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        //토큰생성
        String token = JwtTokenUtils.generateToken(username, secretKey, expiredTimeMs);
        return token;
    }
}
