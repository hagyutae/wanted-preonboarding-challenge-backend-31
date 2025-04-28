package sample.challengewanted.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private boolean success;
    private ErrorDetail error;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorDetail {
        private String code;
        private String message;
        private Object details;
    }

    public static ErrorResponse of(String code, String message, Object details) {
        return new ErrorResponse(false, new ErrorDetail(code, message, details));
    }
}