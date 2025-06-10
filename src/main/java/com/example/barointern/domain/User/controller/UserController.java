package com.example.barointern.domain.User.controller;

import com.example.barointern.common.jwt.JwtUtil;
import com.example.barointern.domain.User.dto.request.LogInRequest;
import com.example.barointern.domain.User.dto.request.SignUpRequest;
import com.example.barointern.domain.User.dto.response.LogInResponse;
import com.example.barointern.domain.User.dto.response.SignUpResponse;
import com.example.barointern.domain.User.entity.User;
import com.example.barointern.domain.User.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/user/sign-up")
    public ResponseEntity<SignUpResponse> userSignUp(
            @RequestBody SignUpRequest dto) {
        User user = userService.userSignUp(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(new SignUpResponse(user.getEmail(),user.getUserRole()));
    }

    @PostMapping("/auth/admin/sign-up")
    public ResponseEntity<SignUpResponse> adminSignUp(
            @RequestBody SignUpRequest dto) {
        User user = userService.adminSignUp(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(new SignUpResponse(user.getEmail(),user.getUserRole()));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LogInResponse> logIn(
            @RequestBody LogInRequest dto,
            HttpServletResponse response) {
        String token = userService.logIn(dto.getEmail(), dto.getPassword());
        ResponseCookie jwtTokenCookie = ResponseCookie.from("TOKEN", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtTokenCookie.toString());
        return ResponseEntity.ok(new LogInResponse(token));
    }
}
