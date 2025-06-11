package com.example.barointern.common.consts;

public interface Const {
    String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$";

    //TODO : 추후 로그인 없이도 방문가능한 페이지의 경우 해당 화이트 리스트에 URL 추가
    String[] WHITE_LIST = {
            "/api/v1/auth/user/sign-up",
            "/api/v1/auth/admin/sign-up",
            "/api/v1/auth/sign-in",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/docs"
    };

}
