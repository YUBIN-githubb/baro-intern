package com.example.barointern.domain.User.controller;

import com.example.barointern.domain.User.dto.request.SignUpRequest;
import com.example.barointern.domain.User.dto.response.SignUpResponse;
import com.example.barointern.domain.User.entity.User;
import com.example.barointern.domain.User.service.UserService;
import lombok.RequiredArgsConstructor;
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
}
