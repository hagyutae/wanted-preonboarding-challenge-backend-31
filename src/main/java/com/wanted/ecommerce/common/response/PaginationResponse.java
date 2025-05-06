package com.wanted.ecommerce.common.response;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PaginationResponse<T> {

    private final List<T> items;
    private final Pagination pagination;

    private PaginationResponse(List<T> items, Pagination pagination) {
        this.items = items;
        this.pagination = pagination;
    }

    public static <T> PaginationResponse<T> of(Page<T>page) {
        Pagination pagination = Pagination.builder()
            .totalItems(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .currentPage(page.getNumber() + 1)
            .perPage(page.getSize())
            .build();

        return new PaginationResponse<>(page.getContent(), pagination);
    }
}