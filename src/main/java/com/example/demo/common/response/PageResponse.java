package com.example.demo.common.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(
        List<T> items,
        PageNation pagination
) {
    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                new PageNation(
                        page.getTotalElements(),
                        (long) page.getTotalPages(),
                        (long) page.getNumber() + 1,
                        (long) page.getSize()
                )
        );
    }

    public record PageNation(
            Long totalItems,
            Long totalPages,
            Long currentPage,
            Long perPage
    ) {
    }
}
