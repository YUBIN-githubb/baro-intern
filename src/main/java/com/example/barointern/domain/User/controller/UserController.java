package com.example.barointern.domain.User.controller;

import com.example.barointern.common.annotation.Auth;
import com.example.barointern.common.dto.AuthUser;
import com.example.barointern.common.jwt.JwtUtil;
import com.example.barointern.domain.User.dto.request.ChangeUserRoleRequest;
import com.example.barointern.domain.User.dto.request.LogInRequest;
import com.example.barointern.domain.User.dto.request.SignUpRequest;
import com.example.barointern.domain.User.dto.response.ChangeUserRoleResponse;
import com.example.barointern.domain.User.dto.response.LogInResponse;
import com.example.barointern.domain.User.dto.response.SignUpResponse;
import com.example.barointern.domain.User.entity.User;
import com.example.barointern.domain.User.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "인증/인가 API", description = "USER 회원가입, ADMIN 회원가입, 로그인, 역할변경(USER->ADMIN) API를 테스트할 수 있습니다.")
public class UserController {

    private final UserService userService;

    @Operation(summary = "USER 회원가입", description = "USER 역할로 회원가입 하는 API입니다.")
    @PostMapping("/auth/user/sign-up")
    public ResponseEntity<SignUpResponse> userSignUp(
            @Valid @RequestBody SignUpRequest dto) {
        User user = userService.userSignUp(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(new SignUpResponse(user.getEmail(),user.getUserRole()));
    }

    @Operation(summary = "ADMIN 회원가입", description = "ADMIN 역할로 회원가입 하는 API입니다.")
    @PostMapping("/auth/admin/sign-up")
    public ResponseEntity<SignUpResponse> adminSignUp(
            @Valid @RequestBody SignUpRequest dto) {
        User user = userService.adminSignUp(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(new SignUpResponse(user.getEmail(),user.getUserRole()));
    }

    @Operation(summary = "로그인", description = "로그인 하는 API입니다.")
    @PostMapping("/auth/login")
    public ResponseEntity<LogInResponse> logIn(
            @Valid @RequestBody LogInRequest dto) {
        String token = userService.logIn(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(new LogInResponse(token));
    }

    @Operation(summary = "역할변경(USER->ADMIN)", description = "역할변경(USER->ADMIN) 하는 API입니다. ADMIN 역할인 사용자만 호출 가능합니다.")
    @PatchMapping("/roles")
    public ResponseEntity<ChangeUserRoleResponse> changeUserRole(
            @Auth AuthUser authUser,
            @Valid @RequestBody ChangeUserRoleRequest dto) {
        User user = userService.changeUserRole(authUser.getUserRole(), dto.getEmail());
        return ResponseEntity.ok(new ChangeUserRoleResponse(user.getEmail(), user.getUserRole()));
    }
}
