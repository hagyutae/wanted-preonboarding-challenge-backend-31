package com.example.preonboarding.response.error;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
@Getter
@NoArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private Map<String, Object> details;


    public ErrorResponse(ErrorCode error,Map<String, Object> details) {
        this.code = error.getCode();
        this.message = error.getMessage();
        this.details = details;
    }
}
