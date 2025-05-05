package sample.challengewanted.dto.common;

import org.springframework.data.domain.Page;

public record PaginationResponse(
        int currentPage,
        int perPage,
        int totalPages,
        long totalItems
) {
    public static PaginationResponse from(Page<?> page) {
        return new PaginationResponse(
                page.getNumber() + 1, // 0-based → 1-based
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}