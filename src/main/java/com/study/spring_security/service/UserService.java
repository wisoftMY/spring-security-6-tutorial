package com.study.spring_security.service;

import com.study.spring_security.controller.dto.UserDto;
import com.study.spring_security.model.Role;
import com.study.spring_security.model.Users;
import com.study.spring_security.repo.RoleRepository;
import com.study.spring_security.repo.UserRepository;
import com.study.spring_security.service.user_converter.UserRegisterDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserDto.UserRegisterResponse register(final UserDto.UserRegisterRequest userDto) {
        String encodedPassword = encoder.encode(userDto.getPassword());
        List<Role> findRoles = roleRepository.findByStatusIn(userDto.getRoles());

        if (findRoles.isEmpty()) {
            throw new IllegalArgumentException("Invalid roles provided");
        }

        Users users = Users.createWithRoles(userDto.getUsername(), encodedPassword, findRoles);
        Users savedUser = userRepository.save(users);

        return UserRegisterDtoConverter.toResponse(savedUser);
    }

    public Map<String, String> verify(Users user) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
                );

        if (authentication.isAuthenticated()) {
            // Access Token과 Refresh Token 생성
            String accessToken = jwtService.generateAccessToken(user.getUsername());
            String refreshToken = jwtService.generateRefreshToken(user.getUsername());

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);

            return tokens;
        }

        throw new RuntimeException("Authentication failed");
    }


    public Map<String, String> tokenRefresh(String refreshToken) {
        if (refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }
        if (jwtService.isValid(refreshToken)) {
            String username = jwtService.extractUserName(refreshToken);
            String newAccessToken = jwtService.generateAccessToken(username);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);
            tokens.put("refreshToken", refreshToken);
            return tokens;
        }
        throw new RuntimeException("Token refresh expired");
    }
}
