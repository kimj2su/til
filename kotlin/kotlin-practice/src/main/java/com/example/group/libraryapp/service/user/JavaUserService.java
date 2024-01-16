package com.example.group.libraryapp.service.user;

import com.example.group.libraryapp.dto.user.response.JavaUserResponse;
import com.example.group.libraryapp.domain.user.JavaUser;
import com.example.group.libraryapp.domain.user.JavaUserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JavaUserService {

  private final JavaUserRepository javaUserRepository;

  public JavaUserService(JavaUserRepository javaUserRepository) {
    this.javaUserRepository = javaUserRepository;
  }

  @Transactional
  public void saveUser(UserCreateRequest request) {
    JavaUser newJavaUser = new JavaUser(request.getName(), request.getAge());
    javaUserRepository.save(newJavaUser);
  }

  @Transactional(readOnly = true)
  public List<JavaUserResponse> getUsers() {
    return javaUserRepository.findAll().stream()
        .map(JavaUserResponse::new)
        .collect(Collectors.toList());
  }

  @Transactional
  public void updateUserName(UserUpdateRequest request) {
    JavaUser javaUser = javaUserRepository.findById(request.getId()).orElseThrow(IllegalArgumentException::new);
    javaUser.updateName(request.getName());
  }

  @Transactional
  public void deleteUser(String name) {
    JavaUser javaUser = javaUserRepository.findByName(name).orElseThrow(IllegalArgumentException::new);
    javaUserRepository.delete(javaUser);
  }

}
