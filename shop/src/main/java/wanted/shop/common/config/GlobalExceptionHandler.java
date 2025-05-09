package wanted.shop.common.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import wanted.shop.common.annotation.ValidationFailureMessage;
import wanted.shop.common.api.ErrorCode;
import wanted.shop.common.api.ErrorResponse;
import wanted.shop.common.api.Message;
import wanted.shop.test.TestController;
import wanted.shop.test.TestException;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static wanted.shop.common.api.ErrorCode.INTERNAL_ERROR;
import static wanted.shop.common.api.ErrorCode.INVALID_INPUT;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {

        ErrorCode httpErrorCode = INTERNAL_ERROR;
        Message message = new Message(ex.getMessage());
        ErrorResponse response = new ErrorResponse(
                httpErrorCode,
                message
        );

        if (request instanceof ServletWebRequest swr) {
            HttpServletRequest req = swr.getRequest();
            HandlerMethod handlerMethod = (HandlerMethod) req.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
            if (handlerMethod != null) {
                ValidationFailureMessage annotation = handlerMethod.getMethodAnnotation(ValidationFailureMessage.class);
                if (annotation != null) {
                    message = new Message(annotation.value());
                }
            }
        }

        if (ex instanceof MethodArgumentNotValidException validException) {
            response = new ErrorResponse(
                    httpErrorCode,
                    message,
                    validException.getBindingResult().getFieldErrors()
            );
        }

        return new ResponseEntity<>(response, httpErrorCode.getStatus());
    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
//
//        ErrorCode httpErrorCode = INVALID_INPUT;
//
//        ErrorResponse response = new ErrorResponse(
//                httpErrorCode,
//                new Message("입력값이 유효하지 않습니다."),
//                ex.getBindingResult().getFieldErrors()
//        );
//
//        return new ResponseEntity<>(response, httpErrorCode.getStatus());
//    }
}

