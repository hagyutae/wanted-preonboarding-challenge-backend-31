package wanted.domain.product.dto.response;

public record Pagination(
        long totalItems,
        int totalPages,
        int currentPage,
        int perPage
) {}
