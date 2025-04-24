package com.wanted.ecommerce.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Pagination {

    private long totalItems;
    private int totalPages;
    private int currentPage;
    private int perPage;

    public Pagination of(long totalItems, int totalPages, int currentPage, int perPage) {
        return Pagination.builder()
            .totalItems(totalItems)
            .totalPages(totalPages)
            .currentPage(currentPage)
            .perPage(perPage)
            .build();
    }
}
