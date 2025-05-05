package wanted.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wanted.common.exception.code.ExceptionCode;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ExceptionCode exceptionCode;
    private final Object details;
}
