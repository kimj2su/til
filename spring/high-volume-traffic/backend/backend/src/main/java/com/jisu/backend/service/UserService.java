package com.jisu.backend.service;

import com.jisu.backend.dto.SignUpUser;
import com.jisu.backend.entity.User;
import com.jisu.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(SignUpUser signUpUser) {
        User user = User.builder()
                .username(signUpUser.username())
                .password(passwordEncoder.encode(signUpUser.password()))
                .email(signUpUser.email())
                .build();
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
