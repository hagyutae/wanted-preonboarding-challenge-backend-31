package com.june.ecommerce.global.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
public class ApiResponse<T> {

    private final boolean success = true;
    private final T data;
    private final String message;

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data, "요청이 성공적으로 처리되었습니다.");
    }

}