package wanted.shop.common.api;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class Error {
    private final String code;
    private final String message;
    private Map<String, List<String>> details;

    public Error(ErrorCode code, Message message) {
        this.code = code.name();
        this.message = message.getValue();
    }

    public Error(ErrorCode code, Message message, List<FieldError> fieldErrors) {
        this.code = code.name();
        this.message = message.getValue();
        this.details = fieldErrors.stream().collect(Collectors.groupingBy(
                FieldError::getField,
                LinkedHashMap::new,
                Collectors.mapping(
                        fieldError -> Optional.ofNullable(fieldError.getDefaultMessage()).orElse(""),
                        Collectors.toList()
                )
        ));
    }
}
