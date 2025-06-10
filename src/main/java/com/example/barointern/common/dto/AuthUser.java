package com.example.barointern.common.dto;

import com.example.barointern.common.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AuthUser {

    private final String email;
    private final UserRole userRole;

    public AuthUser(String email, UserRole userRole) {
        this.email = email;
        this.userRole = userRole;
    }
}
