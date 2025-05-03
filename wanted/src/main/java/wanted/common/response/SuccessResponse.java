package wanted.common.response;

public record SuccessResponse<T>(
        boolean success,
        T data,
        String message
) {
    public static <T> SuccessResponse<T> ok(T data) {
        return new SuccessResponse<>(true, data, "요청이 성공적으로 처리되었습니다.");
    }
}