package cqrs.precourse.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetail {
    private String code;
    private String message;
    private Object detail;

    public ErrorDetail(String code, String message) {
        this(code, message, null);

    }
}
