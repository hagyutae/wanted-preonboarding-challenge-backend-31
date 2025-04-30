package com.mkhwang.wantedcqrs.config.exception;

public class ForbiddenException extends RuntimeException {
  public ForbiddenException(String message) {
    super(message);
  }
}


