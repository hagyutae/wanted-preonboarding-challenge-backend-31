package wanted.common.response;

import java.util.List;

public record PagedResponse<T>(
        boolean success,
        PageData<T> data,
        String message
) {
    public static <T> PagedResponse<T> of(List<T> items, Pagination pagination) {
        return new PagedResponse<>(true, new PageData<>(items, pagination), "요청이 성공적으로 처리되었습니다.");
    }

    public record PageData<T>(
            List<T> items,
            Pagination pagination
    ) {}

    public record Pagination(
            int total_items,
            int total_pages,
            int current_page,
            int per_page
    ) {}
}
