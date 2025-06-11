package com.example.barointern.domain;

import com.example.barointern.common.config.PasswordEncoder;
import com.example.barointern.common.enums.UserRole;
import com.example.barointern.domain.User.dto.request.ChangeUserRoleRequest;
import com.example.barointern.domain.User.dto.request.SignInRequest;
import com.example.barointern.domain.User.dto.request.SignUpRequest;
import com.example.barointern.domain.User.repository.UserRepository;
import com.example.barointern.domain.User.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiEndPointTest {

    // 각 API 엔드포인트 별 올바른 입력과 잘못된 입력에 대해 테스트 케이스를 작성합니다.

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 성공 - USER")
    void successUserSignUp() throws Exception {
        // given
        SignUpRequest request = new SignUpRequest("testuser@example.com", "Qwer1234!!!");

        // when & then
        mockMvc.perform(post("/api/v1/auth/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("testuser@example.com"))
                .andExpect(jsonPath("$.userRole").value("USER"));
    }

    @Test
    @DisplayName("회원가입 성공 - ADMIN")
    void successAdminSignUp() throws Exception {
        // given
        SignUpRequest request = new SignUpRequest("testadmin@example.com", "Qwer1234!!!");

        // when & then
        mockMvc.perform(post("/api/v1/auth/admin/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("testadmin@example.com"))
                .andExpect(jsonPath("$.userRole").value("ADMIN"));
    }

    @Test
    @DisplayName("회원가입 실패 - 이미 가입된 사용자 이메일")
    public void alreadyExistUser() throws Exception {
        // given
        userRepository.save("alreadyExist@example.com", "Qwer1234!!!", UserRole.ADMIN);
        SignUpRequest request = new SignUpRequest("alreadyExist@example.com", "Qwer1234!!!");

        // when & then
        mockMvc.perform(post("/api/v1/auth/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("USER_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.message").value("이미 가입된 사용자 이메일 입니다."));
    }

    @Test
    @DisplayName("회원가입 실패 - 잘못된 형식 이메일")
    public void wrongEmailSignUp() throws Exception {
        // given
        SignUpRequest request = new SignUpRequest("test", "Qwer1234!!!");

        // when & then
        mockMvc.perform(post("/api/v1/auth/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_INPUT"))
                .andExpect(jsonPath("$.message").value("이메일 형식이 올바르지 않습니다."));
    }

    @Test
    @DisplayName("회원가입 실패 - 잘못된 형식 비밀번호")
    public void wrongPasswordSignUp() throws Exception {
        // given
        SignUpRequest request = new SignUpRequest("test1@test.com", "1");

        // when & then
        mockMvc.perform(post("/api/v1/auth/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_INPUT"))
                .andExpect(jsonPath("$.message").value("비밀번호 형식이 올바르지 않습니다."));
    }

    @Test
    @DisplayName("로그인 성공")
    public void SignIn() throws Exception {
        // given
        String encodedPassword = passwordEncoder.encode("Qwer1234!!!");
        userRepository.save("logintest@test.com", encodedPassword, UserRole.ADMIN);
        SignInRequest request = new SignInRequest("logintest@test.com", "Qwer1234!!!");

        // when & then
        mockMvc.perform(post("/api/v1/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(Matchers.startsWith("Bearer ")));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 오류")
    public void wrongPasswordSignIn() throws Exception {
        // given
        String encodedPassword = passwordEncoder.encode("Qwer1234!!!");
        userRepository.save("faillogintest@test.com", encodedPassword, UserRole.ADMIN);
        SignInRequest request = new SignInRequest("faillogintest@test.com", "Wrongpw123!!");

        // when & then
        mockMvc.perform(post("/api/v1/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_CREDENTIALS"))
                .andExpect(jsonPath("$.message").value("비밀번호가 올바르지 않습니다."));
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 사용자")
    public void notExistUserSignIn() throws Exception {
        // given
        SignInRequest request = new SignInRequest("notexist@test.com", "Wrongpw123!!");

        // when & then
        mockMvc.perform(post("/api/v1/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("존재하지 않는 사용자입니다."));
    }

    @Test
    @DisplayName("역할 변경 성공")
    public void changeUserRole() throws Exception {
        // given
        String encodedPassword = passwordEncoder.encode("Qwer1234!!!");
        userRepository.save("changeuser@test.com", encodedPassword, UserRole.USER);
        userRepository.save("changeadmin@test.com", encodedPassword, UserRole.ADMIN);
        String token = userService.signIn("changeadmin@test.com", "Qwer1234!!!");
        ChangeUserRoleRequest request = new ChangeUserRoleRequest("changeuser@test.com");

        // when & then
        mockMvc.perform(patch("/api/v1/roles")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("changeuser@test.com"))
                .andExpect(jsonPath("$.userRole").value("ADMIN"));
    }

    @Test
    @DisplayName("역할 변경 실패 - 권한 없음")
    public void notAuthorizationChangeUserRole() throws Exception {
        // given
        String encodedPassword = passwordEncoder.encode("Qwer1234!!!");
        userRepository.save("changeuser2@test.com", encodedPassword, UserRole.USER);
        userRepository.save("changeuser3@test.com", encodedPassword, UserRole.USER);
        String token = userService.signIn("changeuser3@test.com", "Qwer1234!!!");
        ChangeUserRoleRequest request = new ChangeUserRoleRequest("changeuser2@test.com");

        // when & then
        mockMvc.perform(patch("/api/v1/roles")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("ACCESS_DENIED"))
                .andExpect(jsonPath("$.message").value("관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다."));
    }

    @Test
    @DisplayName("역할 변경 실패 - 존재하지 않는 사용자")
    public void notExistUserChangeUserRole() throws Exception {
        // given
        String encodedPassword = passwordEncoder.encode("Qwer1234!!!");
        userRepository.save("changeuser4@test.com", encodedPassword, UserRole.ADMIN);
        String token = userService.signIn("changeuser4@test.com", "Qwer1234!!!");
        ChangeUserRoleRequest request = new ChangeUserRoleRequest("changeuser0@test.com");

        // when & then
        mockMvc.perform(patch("/api/v1/roles")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("존재하지 않는 사용자입니다."));
    }
}
