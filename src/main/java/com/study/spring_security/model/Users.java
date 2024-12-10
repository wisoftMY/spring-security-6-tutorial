package com.study.spring_security.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;

    public Users(String username, String password, List<UserRole> userRoles) {
        this.username = username;
        this.password = password;
        this.userRoles = userRoles;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRole> userRoles = new ArrayList<>();

    public void changePassword(String password) {
        this.password = password;
    }

    public static Users createWithRoles(String username, String password, List<Role> roles) {
        Users user = new Users(username, password, new ArrayList<>());
        List<UserRole> userRoles = roles.stream()
                .map(role -> new UserRole(user, role))
                .toList();
        user.getUserRoles().addAll(userRoles);
        return user;
    }
}
