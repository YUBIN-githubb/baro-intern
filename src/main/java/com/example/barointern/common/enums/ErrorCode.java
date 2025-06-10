package com.example.barointern.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_ALREADY_EXISTS("이미 가입된 사용자 이메일 입니다.", HttpStatus.CONFLICT),
    INVALID_CREDENTIALS("비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED("관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다. 관리자에게 문의하세요.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TOKEN("유효하지 않은 인증 토큰입니다.",HttpStatus.UNAUTHORIZED),
    INVALID_INPUT("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    AUTH_PARAMETER_MISMATCH("@Auth와 AuthUser 타입은 함께 사용되어야 합니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    ;

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
