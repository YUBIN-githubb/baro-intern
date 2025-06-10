package com.example.barointern.domain.User.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeUserRoleRequest {
    private final String email;
}
