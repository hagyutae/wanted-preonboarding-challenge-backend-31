package com.ecommerce.products.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResponse<T> {
    private boolean success;
    private T data;
    private String message;


    public ResponseEntity<APIResponse<T>> build() {
        return new ResponseEntity<>(this, HttpStatus.OK);
    }
    public ResponseEntity<APIResponse<T>> build(HttpStatus status) {
        return new ResponseEntity<>(this, status);
    }

    public static <T> APIResponse<T> success(T data, String message) {
        return APIResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .build();
    }

    public static <T> APIResponse<T> success(T data) {
        return success(data, "요청이 성공적으로 처리되었습니다.");
    }

    public static APIResponse<Void> success(String message) {
        return APIResponse.<Void>builder()
                .success(true)
                .message(message)
                .build();
    }
}