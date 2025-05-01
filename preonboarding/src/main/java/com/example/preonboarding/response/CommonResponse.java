package com.example.preonboarding.response;

import com.example.preonboarding.domain.ProductPrices;
import com.example.preonboarding.response.error.ErrorResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private ErrorResponse error;

    public CommonResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public CommonResponse(boolean success, ErrorResponse error) {
        this.success = success;
        this.error = error;
    }
    public static <T> CommonResponse<CommonResponse> success(T data,String message) {
        return new CommonResponse(true, data, message);
    }
    public static CommonResponse error(ErrorResponse error) {
        return new CommonResponse(false, error);
    }
}
