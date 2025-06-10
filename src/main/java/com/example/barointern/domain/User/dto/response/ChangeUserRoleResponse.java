package com.example.barointern.domain.User.dto.response;

import com.example.barointern.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeUserRoleResponse {
    private final String email;
    private final UserRole userRole;
}
