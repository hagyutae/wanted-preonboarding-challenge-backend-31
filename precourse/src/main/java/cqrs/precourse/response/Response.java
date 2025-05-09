package cqrs.precourse.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private boolean success;
    private T data;
    private String message;

    public static <T> Response<T> success(T data, String message) {
        return new Response<>(true, data, message);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(true, data, "요청이 성공적으로 처리되었습니다.");
    }

    public static <T> Response<T> fail(ErrorCode errorCode) {
        return new Response<>(false, null, null);
    }
}
