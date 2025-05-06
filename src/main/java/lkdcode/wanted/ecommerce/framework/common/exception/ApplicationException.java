package lkdcode.wanted.ecommerce.framework.common.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
public class ApplicationException extends RuntimeException {
    private final ApplicationResponseCode responseCode;

    public ApplicationException(ApplicationResponseCode responseCode) {
        super(responseCode.name());
        this.responseCode = responseCode;
    }

    public String getCode() {
        return responseCode.getCode();
    }

    public HttpStatus getHttpStatus() {
        return this.responseCode.getHttpStatus();
    }

    public String getDescription() {
        return responseCode.getDescription();
    }
}