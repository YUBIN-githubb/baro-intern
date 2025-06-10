package com.example.barointern.domain.User.controller;

import com.example.barointern.common.auth.AuthArgumentResolver;
import com.example.barointern.common.dto.AuthUser;
import com.example.barointern.common.enums.UserRole;
import com.example.barointern.common.jwt.JwtFilter;
import com.example.barointern.common.jwt.JwtUtil;
import com.example.barointern.domain.User.dto.request.ChangeUserRoleRequest;
import com.example.barointern.domain.User.dto.request.SignInRequest;
import com.example.barointern.domain.User.dto.request.SignUpRequest;
import com.example.barointern.domain.User.entity.User;
import com.example.barointern.domain.User.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("USER 회원가입 성공")
    public void userSignUp() throws Exception {
        // given
        String email = "test@example.com";
        String password = "Qwer1234!!!";
        UserRole userRole = UserRole.USER;
        SignUpRequest dto = new SignUpRequest(email,password);
        User mockUser = new User(dto.getEmail(), dto.getPassword(), userRole);
        when(userService.userSignUp(dto.getEmail(), dto.getPassword())).thenReturn(mockUser);

        // when & then
        mockMvc.perform(post("/api/v1/auth/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(dto.getEmail()))
                .andExpect(jsonPath("$.userRole").value("USER"));
        verify(userService).userSignUp(email,password);
    }

    @Test
    @DisplayName("ADMIN 회원가입 성공")
    public void adminSignUp() throws Exception {
        // given
        String email = "test@example.com";
        String password = "Qwer1234!!!";
        UserRole userRole = UserRole.ADMIN;
        SignUpRequest dto = new SignUpRequest(email,password);
        User mockUser = new User(dto.getEmail(), dto.getPassword(), userRole);
        when(userService.adminSignUp(dto.getEmail(), dto.getPassword())).thenReturn(mockUser);

        // when & then
        mockMvc.perform(post("/api/v1/auth/admin/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(dto.getEmail()))
                .andExpect(jsonPath("$.userRole").value("ADMIN"));
        verify(userService).adminSignUp(email,password);
    }

    @Test
    @DisplayName("로그인 성공")
    void signInTest() throws Exception {
        // given
        String email = "test@example.com";
        String password = "Qwer1234!!!";
        SignInRequest dto = new SignInRequest(email,password);
        String token = "Bearer mock.jwt.token";
        when(userService.signIn(email,password)).thenReturn(token);

        // when & then
        mockMvc.perform(post("/api/v1/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
        verify(userService).signIn(email,password);
    }

    @Test
    @DisplayName("역할 변경 성공")
    public void successChangeUserRole() {
        // TODO : JwtFilter 통과하는 테스트코드 작성
    }
}