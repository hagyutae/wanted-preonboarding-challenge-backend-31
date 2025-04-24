package com.example.wanted_preonboarding_challenge_backend_31.common.dto;

import org.springframework.http.HttpStatus;

public interface ErrorType {

    String getCode();

    String getMessage();

    HttpStatus getHttpStatus();
}
