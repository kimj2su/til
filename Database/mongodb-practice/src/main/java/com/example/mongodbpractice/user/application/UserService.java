package com.example.mongodbpractice.user.application;

import com.example.mongodbpractice.sequence.SequenceGeneratorService;
import com.example.mongodbpractice.user.domain.User;
import com.example.mongodbpractice.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final SequenceGeneratorService sequenceGeneratorService;
    private final UserRepository userRepository;

    public UserService(SequenceGeneratorService sequenceGeneratorService, UserRepository userRepository) {
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        long id = sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME);
        User savedUser = userRepository.save(userDto.toEntity(id));
        return UserDto.from(savedUser);
    }

    @Transactional(readOnly = true)
    public UserDto getUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        return UserDto.from(user);
    }

    public UserDto modifyUser(long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        user.modify(userDto.getName(), userDto.getAge());
        return UserDto.from(userRepository.save(user));
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
