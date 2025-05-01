package com.wanted.mono.global;

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
    private ErrorResponse error;

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
        return new CommonResponse<>(true, data, message, null);
    }

    /**
     * 실패한 API 반환인 경우
     * @param code
     * @param message 넓은 범위의 실패한 이유 설명
     * @param details 각 항목별로 실패한 이유에 대한 설명
     * @return 예시
     * {
     *  "success": false,
     *  "error": {
     *    "code": "INVALID_INPUT",
     *    "message": "상품 등록에 실패했습니다.",
     *    "details": {
     *      "name": "상품명은 필수 항목입니다.",
     *      "base_price": "기본 가격은 0보다 커야 합니다."
     *    }
     *  }
     * }
     */
    public static CommonResponse<?> error(String code, String message, Map<String, String> details) {
        return new CommonResponse<>(false, null, null, new ErrorResponse(code, message, details));
    }

}

