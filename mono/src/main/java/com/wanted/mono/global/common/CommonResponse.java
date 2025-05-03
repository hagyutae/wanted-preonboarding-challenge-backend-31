package com.wanted.mono.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private boolean success;
    private T data;
    private String message;

    /**
     * 성공적인 API 반환인 경우
     * @param data
     * @param message
     * @return 예시
     * {
     *  "success": true,
     *  "data": {
     *    "id": 123,
     *    "name": "슈퍼 편안한 소파",
     *    "slug": "super-comfortable-sofa",
     *    "created_at": "2025-04-14T09:30:00Z",
     *    "updated_at": "2025-04-14T09:30:00Z"
     *  },
     *  "message": "상품이 성공적으로 등록되었습니다."
     * }
     */
    public static <T> CommonResponse<T> success(T data, String message) {
        return new CommonResponse<>(true, data, message);
    }

}

