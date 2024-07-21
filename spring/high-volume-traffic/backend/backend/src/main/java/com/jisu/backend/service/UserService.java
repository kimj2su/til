package com.jisu.backend.service;

import com.jisu.backend.entity.User;
import com.jisu.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(String username, String password, String email){
        User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
