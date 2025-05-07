package cqrs.precourse.response;

public class ErrorResponse {
    private boolean success;
    private ErrorDetail error;

    public ErrorResponse(ErrorDetail error) {
        this.success = false;
        this.error = error;
    }

    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(new ErrorDetail(code, message));
    }

    public static ErrorResponse of(String code, String message, Object details) {
        return new ErrorResponse(new ErrorDetail(code, message, details));
    }
}
