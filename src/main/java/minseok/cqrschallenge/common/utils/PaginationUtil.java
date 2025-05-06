package minseok.cqrschallenge.common.utils;

import java.util.List;
import minseok.cqrschallenge.common.dto.PaginationResponse;
import org.springframework.data.domain.Page;

public class PaginationUtil {

    public static <T, R> PaginationResponse<R> paginate(Page<T> page, List<R> items) {
        return PaginationResponse.<R>builder()
            .items(items)
            .pagination(PaginationResponse.Pagination.builder()
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber() + 1)
                .perPage(page.getSize())
                .build())
            .build();
    }

    public static <T> PaginationResponse<T> paginate(Page<T> page) {
        return PaginationResponse.<T>builder()
            .items(page.getContent())
            .pagination(PaginationResponse.Pagination.builder()
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber() + 1)
                .perPage(page.getSize())
                .build())
            .build();
    }
}