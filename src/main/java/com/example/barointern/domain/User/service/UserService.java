package com.example.barointern.domain.User.service;

import com.example.barointern.common.config.PasswordEncoder;
import com.example.barointern.common.enums.UserRole;
import com.example.barointern.domain.User.entity.User;
import com.example.barointern.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User userSignUp(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return userRepository.save(email, encodedPassword, UserRole.USER);
    }
}
