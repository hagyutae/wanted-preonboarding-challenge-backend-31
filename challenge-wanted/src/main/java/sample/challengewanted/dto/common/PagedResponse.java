package sample.challengewanted.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponse<T> {
    private boolean success;
    private PagingData<T> data;
    private String message;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PagingData<T> {
        private List<T> items;
        private Pagination pagination;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pagination {
        private int totalItems;
        private int totalPages;
        private int currentPage;
        private int perPage;
    }

    public static <T> PagedResponse<T> success(List<T> items, int totalItems, int totalPages, int currentPage, int perPage) {
        Pagination pagination = new Pagination(totalItems, totalPages, currentPage, perPage);
        return new PagedResponse<>(true, new PagingData<>(items, pagination), "요청이 성공적으로 처리되었습니다.");
    }

    public static PagedResponse<?> failure(String message) {
        return new PagedResponse<>(false, null, message);
    }
}
