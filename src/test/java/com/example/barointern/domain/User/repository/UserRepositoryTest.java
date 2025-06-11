package com.example.barointern.domain.User.repository;

import com.example.barointern.common.enums.UserRole;
import com.example.barointern.common.exception.CustomException;
import com.example.barointern.domain.User.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    // Repository 단위 테스트를 작성합니다.

    @InjectMocks
    private UserRepository userRepository;

    @Test
    @DisplayName("정상적인 유저 정보 저장")
    public void save() {
        // given
        String email = "test@example.com";
        String password = "encodedPassword";
        UserRole userRole = UserRole.USER;

        // when
        User user = userRepository.save(email, password, userRole);

        // then
        Assertions.assertThat(user.getEmail()).isEqualTo(email);
        Assertions.assertThat(user.getPassword()).isEqualTo(password);
        Assertions.assertThat(user.getUserRole()).isEqualTo(userRole);
    }

    @Test
    @DisplayName("이미 가입된 사용자 이메일인 경우")
    public void saveAlreadyExistsEmail() {
        // given
        String email1 = "test@example.com";
        String email2 = "test@example.com";
        String password = "encodedPassword";
        UserRole userRole = UserRole.USER;
        userRepository.save(email1, password, userRole);

        // when & then
        Exception ex =  assertThrows(CustomException.class, () -> {
            userRepository.save(email2, password, userRole);
        });
        assertEquals("이미 가입된 사용자 이메일 입니다.", ex.getMessage());
    }

    @Test
    @DisplayName("이메일로 사용자 찾기")
    public void findByEmail() {
        // given
        String email = "test@example.com";
        String password = "encodedPassword";
        UserRole userRole = UserRole.USER;
        User savedUser = userRepository.save(email, password, userRole);

        // when
        User foundUser = userRepository.findByEmail(savedUser.getEmail());

        // then
        Assertions.assertThat(foundUser.getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    @DisplayName("존재하지 않는 이메일 찾기")
    public void failToFindByEmail() {
        // given
        String email1 = "test1@example.com";
        String email2 = "test2@example.com";
        String password = "encodedPassword";
        UserRole userRole = UserRole.USER;
        userRepository.save(email1, password, userRole);

        // when & then
        Exception ex =  assertThrows(CustomException.class, () -> {
            userRepository.findByEmail(email2);
        });
        assertEquals("존재하지 않는 사용자입니다.", ex.getMessage());
    }
}