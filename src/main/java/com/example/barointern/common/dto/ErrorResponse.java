package com.example.barointern.common.dto;

import com.example.barointern.common.enums.ErrorCode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final String status;
    private final ErrorCode code;
    private final String message;
    private final LocalDateTime timestamp;

    @Builder
    private ErrorResponse(String status, ErrorCode code, String message, LocalDateTime timestamp) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }
}

