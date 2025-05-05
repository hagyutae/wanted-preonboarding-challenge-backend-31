package com.example.cqrsapp.common.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class APIDataResponse<T> {
    private boolean success;
    private T data;
    private String message;

    private APIDataResponse(T data, String message) {
        this.success = true;
        this.data = data;
        this.message = message;
    }

    public static <T> APIDataResponse<T> success(T data) {
        return success(data, "요청이 성공적으로 처리되었습니다.");
    }

    public static <T> APIDataResponse<T> success(T data, String message) {
        return new APIDataResponse<>(data, message);
    }

    public static <T> APIDataResponse<T> success(String message) {
        return new APIDataResponse<>(null, message);
    }
}
