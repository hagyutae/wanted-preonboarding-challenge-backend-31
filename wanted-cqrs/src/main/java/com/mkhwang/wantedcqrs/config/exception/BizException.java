package com.mkhwang.wantedcqrs.config.exception;

import org.springframework.http.HttpStatus;

public class BizException extends RuntimeException {

  private final String code;
  private final HttpStatus status;
  private final Object[] args;


  public BizException(HttpStatus status, String code, Throwable cause, Object[] args) {
    super("", cause);
    this.code = code;
    this.status = status;
    this.args = args;
  }

  public BizException(String code) {
    this(HttpStatus.BAD_REQUEST, code, null, null);
  }

  public BizException(String code, Object[] args) {
    this(HttpStatus.BAD_REQUEST, code, null, args);
  }

  public BizException(HttpStatus status, String code, Object[] args) {
    this(status, code, null, args);
  }

  public String getCode() {
    return code;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public Object[] getArgs() {
    return args;
  }
}
