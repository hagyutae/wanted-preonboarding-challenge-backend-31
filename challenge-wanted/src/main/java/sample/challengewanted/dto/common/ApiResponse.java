package sample.challengewanted.dto.common;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private HttpStatus status;
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK, HttpStatus.OK.value(),"요청이 성공적으로 처리되었습니다.", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(HttpStatus.OK, HttpStatus.OK.value(), message, data);
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),message, null);
    }
}