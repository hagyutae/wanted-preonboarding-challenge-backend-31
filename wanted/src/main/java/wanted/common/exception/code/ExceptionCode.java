package wanted.common.exception.code;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {
    String getCode();
    HttpStatus getStatus();
    String getMessage();
    String getDetails();
}
