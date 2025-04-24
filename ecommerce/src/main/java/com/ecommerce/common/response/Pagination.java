package com.ecommerce.common.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pagination {
    private long totalItems;
    private int totalPages;
    private int currentPage;
    private int perPage;

    @Builder
    public Pagination(long totalItems, int totalPages, int currentPage, int perPage) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.perPage = perPage;
    }
}
