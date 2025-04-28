package com.mkhwang.wantedcqrs.config.exception;

import java.util.Map;

public class ForbiddenException extends RuntimeException {
  private final Map<String, Object> details;

  public ForbiddenException(String message, Map<String, Object> details) {
    super(message);
    this.details = details;
  }

}


