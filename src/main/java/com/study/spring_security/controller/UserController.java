package com.study.spring_security.controller;

import com.study.spring_security.controller.dto.UserDto;
import com.study.spring_security.model.Users;
import com.study.spring_security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserDto.UserRegisterResponse register(final @RequestBody UserDto.UserRegisterRequest userDto) {
        return userService.register(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Users user) {
        Map<String, String> tokens = userService.verify(user);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestHeader("Authorization") String refreshToken) {
        Map<String, String> tokenRefresh = userService.tokenRefresh(refreshToken);
        return ResponseEntity.ok(tokenRefresh);
    }

}
