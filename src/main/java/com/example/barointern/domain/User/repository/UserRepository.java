package com.example.barointern.domain.User.repository;

import com.example.barointern.common.enums.ErrorCode;
import com.example.barointern.common.enums.UserRole;
import com.example.barointern.common.exception.CustomException;
import com.example.barointern.domain.User.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserRepository {

    private final Map<String, User> userMap = new ConcurrentHashMap<>();

    public User save(String email, String password, UserRole userRole) {
        User user = new User(email, password, userRole);

        User existingUser = userMap.putIfAbsent(email, user);
        if (existingUser != null) {
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
        }

        return user;
    }

    public User findByEmail(String email) {
        User foundUser = userMap.get(email);
        if (foundUser == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return foundUser;
    }
}
