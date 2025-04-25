package com.mkhwang.wantedcqrs.config.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ResourceNotFoundException extends RuntimeException {
  private final Map<String, Object> details;

  public ResourceNotFoundException(String message, Map<String, Object> details) {
    super(message);
    this.details = details;
  }

}
