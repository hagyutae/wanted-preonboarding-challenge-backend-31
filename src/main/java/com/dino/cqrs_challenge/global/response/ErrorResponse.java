package com.dino.cqrs_challenge.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 공통 에러 응답 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private boolean success;
    private ErrorDetail error;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDetail {
        private ErrorCode code;
        private String message;
        private Map<String, Object> details; // 선택적 추가 정보
    }
}