package wanted.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        boolean success,
        ErrorDetail error
) {
    public static ErrorResponse of(String code, String message, Object details) {
        return new ErrorResponse(false, new ErrorDetail(code, message, details));
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ErrorDetail(
            String code,
            String message,
            Object details
    ) {}
}
