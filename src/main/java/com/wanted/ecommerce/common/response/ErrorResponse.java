package com.wanted.ecommerce.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse<T> {

    private boolean success;
    private T error;

    protected ErrorResponse(T error){
        this.error = error;
    }


    public static <T> ErrorResponse<T> failure(T error) {
        return ErrorResponse.<T>builder()
            .success(false)
            .error(error)
            .build();
    }
}
