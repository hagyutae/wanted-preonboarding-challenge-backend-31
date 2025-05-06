package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value;

import lombok.Builder;

@Builder
public record Pagination(
    long totalItems,
    long totalPages,
    long currentPage,
    long size
) {
}
