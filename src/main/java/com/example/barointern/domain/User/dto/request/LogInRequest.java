package com.example.barointern.domain.User.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogInRequest {
    private final String email;
    private final String password;
}
