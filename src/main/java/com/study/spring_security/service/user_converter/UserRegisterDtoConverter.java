package com.study.spring_security.service.user_converter;

import com.study.spring_security.controller.dto.UserDto;
import com.study.spring_security.model.RoleStatus;
import com.study.spring_security.model.Users;

import java.util.List;

public class UserRegisterDtoConverter {
    public static UserDto.UserRegisterResponse toResponse(Users user) {
        List<RoleStatus> roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getStatus())
                .toList();
        return new UserDto.UserRegisterResponse(
                user.getId(),
                user.getUsername(),
                roles
        );
    }
}
