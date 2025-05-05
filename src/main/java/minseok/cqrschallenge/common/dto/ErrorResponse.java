package minseok.cqrschallenge.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String code;
    private String message;
    private Object details;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}