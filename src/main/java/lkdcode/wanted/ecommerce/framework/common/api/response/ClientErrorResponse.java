package lkdcode.wanted.ecommerce.framework.common.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonPropertyOrder(value = {"success", "error"})
public class ClientErrorResponse<T> {
    private final Boolean success = false;
    private final Error<T> error;

    private ClientErrorResponse(Error<T> error) {
        this.error = error;
    }

    public static <T> ClientErrorResponse<T> from(String code, String message, T details) {
        return new ClientErrorResponse<>(
            Error.<T>builder()
                .code(code)
                .message(message)
                .details(details)
                .build()
        );
    }

    public static <T> ClientErrorResponse<T> invalidInput(final T details) {
        return new ClientErrorResponse<>(
            Error.<T>builder()
                .code("INVALID_INPUT")
                .message("입력 데이터가 유효하지 않습니다.")
                .details(details)
                .build()
        );
    }

    public static <T> ClientErrorResponse<T> from(String code, String message) {
        return new ClientErrorResponse<>(
            Error.<T>builder()
                .code(code)
                .message(message)
                .build()
        );
    }

    @Builder
    public record Error<T>(
        String code,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T details
    ) {
    }
}