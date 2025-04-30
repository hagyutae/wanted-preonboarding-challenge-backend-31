package com.mkhwang.wantedcqrs.config.advice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
  private String code;
  private String message;
  private Object details;

  public static ApiError of(String code, String message, Object details) {
    return new ApiError(code, message, details);
  }

  public static ApiError of(String code, String message) {
    return new ApiError(code, message, null);
  }
}