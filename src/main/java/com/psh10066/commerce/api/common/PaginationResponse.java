package com.psh10066.commerce.api.common;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record PaginationResponse<T>(
    List<T> items,
    Pagination pagination
) {

    public static <T, R> PaginationResponse<R> of(Page<T> page, Function<T, R> mapper) {
        return new PaginationResponse<>(
            page.getContent().stream().map(mapper).toList(),
            new Pagination(page.getTotalElements(), page.getTotalPages(), page.getNumber() + 1, page.getSize())
        );
    }

    public record Pagination(
        long totalItems,
        int totalPages,
        int currentPage,
        int perPage
    ) {
    }
}