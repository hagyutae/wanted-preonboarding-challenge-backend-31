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
    public ResponseEntity<CommonResponse> handleBadRequestException(BadRequestException e){
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_INPUT, e.getDetails());
        return ResponseEntity.status(ErrorCode.INVALID_INPUT.getStatus())
                .body(CommonResponse.error(errorResponse));
    }

    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseEntity<CommonResponse> handleNotFoundException(NotFoundResourceException e){
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.RESOURCE_NOT_FOUND, null);
        return ResponseEntity.status(ErrorCode.RESOURCE_NOT_FOUND.getStatus())
                .body(CommonResponse.error(errorResponse));
    }

    @ExceptionHandler(ProductRegisterException.class)
    public ResponseEntity<CommonResponse> handleProductRegisterException(ProductRegisterException e){
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.DUPLICATE_PRODUCT,e.getDetails());
        return ResponseEntity.status(ErrorCode.DUPLICATE_PRODUCT.getStatus())
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
