package com.mkhwang.wantedcqrs.config.advice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
public class ApiPageResponse<T> {

  // getter
  private boolean success;
  private ApiPageData<T> data;
  private String message;

  public ApiPageResponse(boolean success, ApiPageData<T> data, String message) {
    this.success = success;
    this.data = data;
    this.message = message;
  }

  public static <T> ApiPageResponse<T> of(Page<T> page, String message) {
    ApiPageData<T> apiPageData = new ApiPageData<>(page);
    return new ApiPageResponse<>(true, apiPageData, message);
  }

}