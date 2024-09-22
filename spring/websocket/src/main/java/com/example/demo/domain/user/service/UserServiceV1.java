package com.example.demo.domain.user.service;

import org.springframework.stereotype.Service;

import com.example.demo.common.exception.ErrorCode;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.user.model.response.UserSearchResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceV1 {

    private final UserRepository userRepository;

    public final UserSearchResponse searchUser(String name, String user) {
        List<String> names = userRepository.findNameByNameMatch(name,user);
        return new UserSearchResponse(ErrorCode.SUCCESS, names);
    }
}
