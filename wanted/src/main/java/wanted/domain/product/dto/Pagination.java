package wanted.domain.product.dto;

public record Pagination(
        long totalItems,
        int totalPages,
        int currentPage,
        int perPage
) {}
