package minseok.cqrschallenge.common.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationResponse<T> {

    private List<T> items;

    private Pagination pagination;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Pagination {

        private long totalItems;

        private int totalPages;

        private int currentPage;

        private int perPage;
    }
}