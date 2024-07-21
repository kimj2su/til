package com.jisu.backend.controller;

import com.jisu.backend.entity.User;
import com.jisu.backend.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestParam String username, @RequestParam String password, @RequestParam String email){
        return ResponseEntity.ok(userService.createUser(username, password, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user th be deleted", required = true) @PathVariable Long id
    ){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
