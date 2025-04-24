package com.ecommerce.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageResponse<T> {
    private List<T> items;
    private Pagination pagination;

    private PageResponse(List<T> items, Pagination pagination) {
        this.items = items;
        this.pagination = pagination;
    }

    public static <T> PageResponse<T> of(List<T> items, Pagination pagination) {
        return new PageResponse<>(items, pagination);
    }

    public static <T> PageResponse<T> of(Page<T> page) {
        Pagination pagination = Pagination.builder()
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber() + 1)
                .perPage(page.getSize())
                .build();

        return new PageResponse<>(page.getContent(), pagination);
    }
}
