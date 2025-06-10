package com.example.barointern.domain.User.dto.request;

import com.example.barointern.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignUpRequest {
    private final String email;
    private final String password;
}
