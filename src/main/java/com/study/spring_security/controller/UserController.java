package com.study.spring_security.controller;

import com.study.spring_security.controller.dto.UserDto;
import com.study.spring_security.model.Users;
import com.study.spring_security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserDto.UserRegisterResponse register(final @RequestBody UserDto.UserRegisterRequest userDto) {
        return userService.register(userDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return userService.verify(user);
    }
}
