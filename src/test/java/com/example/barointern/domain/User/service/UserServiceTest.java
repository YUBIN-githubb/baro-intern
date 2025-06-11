package com.example.barointern.domain.User.service;

import com.example.barointern.common.config.PasswordEncoder;
import com.example.barointern.common.enums.UserRole;
import com.example.barointern.common.exception.CustomException;
import com.example.barointern.common.jwt.JwtUtil;
import com.example.barointern.domain.User.entity.User;
import com.example.barointern.domain.User.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // Service 단위 테스트를 작성합니다.

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("정상적인 회원가입 (USER)")
    public void signUpUser() {
        // given
        String email = "test@test.com";
        String password = "Qwer1234!!!";
        String encodedPassword = "encodedPasswordQwer1234!!!";
        User expectedUser = new User(email, encodedPassword, UserRole.USER);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(email, encodedPassword, UserRole.USER)).thenReturn(expectedUser);

        // when
        User user = userService.userSignUp(email, password);

        // then
        Assertions.assertThat(email).isEqualTo(user.getEmail());
        Assertions.assertThat(encodedPassword).isEqualTo(user.getPassword());
        Assertions.assertThat(UserRole.USER).isEqualTo(user.getUserRole());
    }

    @Test
    @DisplayName("정상적인 회원가입 (ADMIN)")
    public void signUpAdmin() {
        // given
        String email = "test@test.com";
        String password = "Qwer1234!!!";
        String encodedPassword = "encodedPasswordQwer1234!!!";
        User expectedUser = new User(email, encodedPassword, UserRole.ADMIN);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(email, encodedPassword, UserRole.ADMIN)).thenReturn(expectedUser);

        // when
        User user = userService.adminSignUp(email, password);

        // then
        Assertions.assertThat(email).isEqualTo(user.getEmail());
        Assertions.assertThat(encodedPassword).isEqualTo(user.getPassword());
        Assertions.assertThat(UserRole.ADMIN).isEqualTo(user.getUserRole());
    }

    @Test
    @DisplayName("정상적인 로그인")
    public void signIn() {
        // given
        String email = "test@test.com";
        String password = "Qwer1234!!!";
        String encodedPassword = "encodedPasswordQwer1234!!!";
        UserRole userRole = UserRole.USER;
        User expectedUser = new User(email, encodedPassword, userRole);
        when(userRepository.findByEmail(email)).thenReturn(expectedUser);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtUtil.createToken(email,userRole)).thenReturn("JWT-TOKEN");

        // when
        String token = userService.signIn(email, password);

        // then
        Assertions.assertThat(token).isEqualTo("JWT-TOKEN");
    }

    @Test
    @DisplayName("비정상적인 로그인 (비밀번호 오류)")
    public void failToSignIn() {
        // given
        String email = "test@test.com";
        String password = "Qwer1234!!!";
        String wrongPassword = "Abcd1234!!!";
        String encodedPassword = "encodedPasswordQwer1234!!!";
        UserRole userRole = UserRole.USER;
        User expectedUser = new User(email, encodedPassword, userRole);
        when(userRepository.findByEmail(email)).thenReturn(expectedUser);
        when(passwordEncoder.matches(wrongPassword, encodedPassword)).thenReturn(false);

        // when & then
        Exception ex =  assertThrows(CustomException.class, () -> {
            userService.signIn(email, wrongPassword);
        });
        assertEquals("비밀번호가 올바르지 않습니다.", ex.getMessage());
    }

    @Test
    @DisplayName("정상적인 역할 변경")
    public void changeUserRole() {
        // given
        String email = "test@test.com";
        String encodedPassword = "encodedPasswordQwer1234!!!";
        User expectedUser = new User(email, encodedPassword, UserRole.USER);
        when(userRepository.findByEmail(email)).thenReturn(expectedUser);

        // when
        User user = userService.changeUserRole(UserRole.ADMIN, email);

        // then
        Assertions.assertThat(user.getUserRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    @DisplayName("비정상적인 역할 변경 (권한없음)")
    public void failToChangeUserRole() {
        // given
        String email = "test@test.com";

        // when & then
        Exception ex =  assertThrows(CustomException.class, () -> {
            userService.changeUserRole(UserRole.USER, email);
        });
        assertEquals("관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다.", ex.getMessage());
    }
}