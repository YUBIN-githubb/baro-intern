package com.example.barointern.domain.User.service;

import com.example.barointern.common.config.PasswordEncoder;
import com.example.barointern.common.enums.ErrorCode;
import com.example.barointern.common.enums.UserRole;
import com.example.barointern.common.exception.CustomException;
import com.example.barointern.common.jwt.JwtUtil;
import com.example.barointern.domain.User.entity.User;
import com.example.barointern.domain.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User userSignUp(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return userRepository.save(email, encodedPassword, UserRole.USER);
    }

    public User adminSignUp(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return userRepository.save(email, encodedPassword, UserRole.ADMIN);
    }

    public String logIn(String email, String password) {
        User foundUser = userRepository.findByEmail(email);
        if (!passwordEncoder.matches(password, foundUser.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }
        String token = jwtUtil.createToken(email, foundUser.getUserRole());
        return jwtUtil.substringToken(token);
    }
}
