package com.study.spring_security.controller.dto;

import com.study.spring_security.model.RoleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserDto {

    private UserDto() {
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRegisterRequest {

        private String username;

        private String password;

        private List<RoleStatus> roles; // List of roles to be assigned to the user
    }

    @Getter
    public static class UserRegisterResponse {

        private final Long id;
        private final String username;
        private final List<RoleStatus> roles; // List of roles assigned to the user

        public UserRegisterResponse(final Long id, final String username, final List<RoleStatus> roles) {
            this.id = id;
            this.username = username;
            this.roles = roles;
        }
    }

}
