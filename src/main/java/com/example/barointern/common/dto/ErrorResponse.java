package com.example.barointern.common.dto;

import com.example.barointern.common.enums.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private ErrorDetail error;

    public ErrorResponse(ErrorCode code, String message) {
        this.error = new ErrorDetail(code, message);
    }

    public static class ErrorDetail {
        private ErrorCode code;
        private String message;

        public ErrorDetail(ErrorCode code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}

