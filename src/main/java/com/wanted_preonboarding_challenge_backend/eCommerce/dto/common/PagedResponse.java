package com.wanted_preonboarding_challenge_backend.eCommerce.dto.common;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponse<T> {
    private List<T> items;
    private PaginationDto pagination;

    public static <T> PagedResponse<T> of(Page<T> page) {
        return PagedResponse.<T>builder()
                .items(page.getContent())
                .pagination(PaginationDto.builder()
                        .totalItems(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .currentPage(page.getNumber() + 1)
                        .perPage(page.getSize())
                        .build())
                .build();
    }
}