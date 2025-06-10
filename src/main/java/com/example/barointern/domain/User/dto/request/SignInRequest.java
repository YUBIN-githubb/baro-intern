package com.example.barointern.domain.User.dto.request;

import com.example.barointern.common.consts.Const;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInRequest {
    @Schema(description = "로그인 시 필요한 사용자 이메일입니다.", example = "example@example.com")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일 입력은 필수입니다.")
    private final String email;

    @Schema(description = "로그인 시 필요한 사용자 비밀번호입니다.", example = "Qwer1234!!!")
    @Pattern(
            regexp = Const.PASSWORD_PATTERN,
            message = "비밀번호 형식이 올바르지 않습니다."
    )
    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private final String password;
}
