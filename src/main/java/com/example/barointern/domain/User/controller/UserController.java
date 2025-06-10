package com.example.barointern.domain.User.controller;

import com.example.barointern.domain.User.dto.request.UserSignUpRequest;
import com.example.barointern.domain.User.dto.response.UserSignUpResponse;
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
    public ResponseEntity<UserSignUpResponse> signUp(
            @RequestBody UserSignUpRequest dto) {
        User user = userService.userSignUp(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(new UserSignUpResponse(user.getEmail(),user.getUserRole()));
    }
}
