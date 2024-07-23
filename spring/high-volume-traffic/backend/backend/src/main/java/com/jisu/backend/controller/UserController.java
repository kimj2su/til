package com.jisu.backend.controller;

import com.jisu.backend.dto.SignUpUser;
import com.jisu.backend.entity.User;
import com.jisu.backend.jwt.JwtUtil;
import com.jisu.backend.service.BlackListService;
import com.jisu.backend.service.CustomUserDetailsService;
import com.jisu.backend.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final BlackListService blackListService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/signUp")
    public ResponseEntity<User> createUser(@RequestBody SignUpUser signUpUser){
        return ResponseEntity.ok(userService.createUser(signUpUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user th be deleted", required = true) @PathVariable Long id
    ){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtUtil.generateToken(userDetails.getUsername());

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        // cookie.setSecure(true);
        cookie.setPath("/");
        // cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setMaxAge(60 * 60);

        response.addCookie(cookie);

        return token;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 쿠키 삭제

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout/all")
    public ResponseEntity<Void> logoutAll(@RequestParam(required = false) String requestToken, @CookieValue(value = "token", required = false) String cookieToken, HttpServletResponse response) {
        String token = requestToken != null ? requestToken : cookieToken;
        Instant instant = new Date().toInstant();
        LocalDateTime expirationTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        // LocalDateTime localDateTime = jwtUtil.getExpirationDateFromToken(token).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        blackListService.blackListToken(token, expirationTime, jwtUtil.getUsername(token));
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 쿠키 삭제

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/validation")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity jwtValidate(@RequestParam String token){
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This username is not allowed.");
    }
}
