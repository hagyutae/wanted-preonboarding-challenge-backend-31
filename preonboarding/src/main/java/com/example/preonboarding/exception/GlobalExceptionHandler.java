package com.example.preonboarding.exception;

import com.example.preonboarding.response.CommonResponse;
import com.example.preonboarding.response.error.ErrorCode;
import com.example.preonboarding.response.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResponse> handleNotFoundException(BadRequestException e){
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_INPUT, null);
        return ResponseEntity.status(ErrorCode.INTERNAL_ERROR.getStatus())
                .body(CommonResponse.error(errorResponse));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleException(Exception e){
        e.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_ERROR,null);
        return ResponseEntity.status(ErrorCode.INTERNAL_ERROR.getStatus())
                .body(CommonResponse.error(errorResponse));
    }
}
