package com.example.barointern.domain.User.entity;

import com.example.barointern.common.enums.UserRole;
import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String email;
    private String password;
    private UserRole userRole;

    public User(Long id, String email, String password, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }
}
