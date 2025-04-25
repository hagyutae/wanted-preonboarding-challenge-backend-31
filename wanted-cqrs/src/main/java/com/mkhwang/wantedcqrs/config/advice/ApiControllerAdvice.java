package com.mkhwang.wantedcqrs.config.advice;

import com.mkhwang.wantedcqrs.config.advice.dto.ApiPageResponse;
import com.mkhwang.wantedcqrs.config.advice.dto.ApiResponse;
import com.mkhwang.wantedcqrs.config.i18n.I18nService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Component
@RequiredArgsConstructor
@RestControllerAdvice
public class ApiControllerAdvice implements ResponseBodyAdvice<Object> {
  private final I18nService i18nService;

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request, ServerHttpResponse response) {
    if (body instanceof ApiResponse || body instanceof ApiPageResponse) {
      return body;
    }

    ApiMessage annotation = returnType.getMethodAnnotation(ApiMessage.class);
    String messageKey = annotation != null ? annotation.value() : "default.success";
    String message = i18nService.getMessage(messageKey);

    if (body instanceof Page<?> page) {
      return ApiPageResponse.of(page, message);
    }

    return ApiResponse.of(body, message);
  }
}
