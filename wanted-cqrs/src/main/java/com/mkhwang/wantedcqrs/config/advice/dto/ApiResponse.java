package com.mkhwang.wantedcqrs.config.advice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

  private boolean success;
  private T data;
  private String message;
  private ApiError error;

  public static ApiResponse<?> error(String code, String message) {
    return new ApiResponse<>(false, null, null, ApiError.of(code, message));
  }

  public static ApiResponse<?> error(String code, String message, Object details) {
    return new ApiResponse<>(false, null, null, ApiError.of(code, message, details));
  }

  public static <T> ApiResponse<T> of(T data, String message) {
    return new ApiResponse<>(true, data, message, null);
  }
}
