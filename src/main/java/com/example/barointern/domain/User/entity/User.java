package com.example.barointern.domain.User.entity;

import com.example.barointern.common.enums.UserRole;
import lombok.Getter;

@Getter
public class User {

    private String email;
    private String password;
    private UserRole userRole;

    public User(String email, String password, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public void changeUserRole (UserRole userRole) {
        this.userRole = userRole;
    }
}
