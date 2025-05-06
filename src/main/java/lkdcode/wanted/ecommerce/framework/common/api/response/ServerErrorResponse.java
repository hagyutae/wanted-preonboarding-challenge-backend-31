package lkdcode.wanted.ecommerce.framework.common.api.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder(value = {"success", "error"})
public class ServerErrorResponse<T> {
    private final Boolean success = false;
    private final T error;

    private ServerErrorResponse(T error) {
        this.error = error;
    }

    public static <T> ServerErrorResponse<T> from(T error) {
        return new ServerErrorResponse<>(error);
    }
}
