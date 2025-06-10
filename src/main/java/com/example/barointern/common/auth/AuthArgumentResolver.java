package com.example.barointern.common.auth;

import com.example.barointern.common.annotation.Auth;
import com.example.barointern.common.dto.AuthUser;
import com.example.barointern.common.enums.ErrorCode;
import com.example.barointern.common.enums.UserRole;
import com.example.barointern.common.exception.CustomException;
import jakarta.annotation.Nullable;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAuthAnnotation = parameter.getParameterAnnotation(Auth.class) != null;
        boolean isAuthUserType = parameter.getParameterType().equals(AuthUser.class);

        if (hasAuthAnnotation != isAuthUserType) {
            throw new CustomException(ErrorCode.AUTH_PARAMETER_MISMATCH);
        }

        return hasAuthAnnotation;
    }

    @Override
    public Object resolveArgument(
            @Nullable MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String email = (String) request.getAttribute("email");
        UserRole userRole = UserRole.valueOf((String) request.getAttribute("userRole"));

        return new AuthUser(email, userRole);
    }
}
