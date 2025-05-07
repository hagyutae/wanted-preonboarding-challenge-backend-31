package wanted.shop.common.api;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@JsonPropertyOrder({ "success", "error" })
public class ErrorResponse {
    private final boolean success = false;
    private final Error error;

    public ErrorResponse(ErrorCode code, Message message) {
        this.error = new Error(code, message);
    }

    public ErrorResponse(ErrorCode code, Message message, List<FieldError> fieldErrors) {
        this.error = new Error(code, message, fieldErrors);
    }

}
