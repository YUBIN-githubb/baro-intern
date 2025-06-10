package com.example.barointern.domain.User.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeUserRoleRequest {
    @Schema(description = "역할 변경할 사용자 이메일입니다.", example = "example@example.com")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일 입력은 필수입니다.")
    private final String email;
}
