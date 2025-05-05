package com.example.cqrsapp.common.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResponseDto<T> {
    private List<T> item;
    private PaginationDto pagination;

    private PageResponseDto(Page<T> page) {
        this.item = page.getContent();
        this.pagination = PaginationDto.builder()
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .perPage(page.getNumberOfElements())
                .build();
    }

    public static <F> PageResponseDto<F> from(Page<F> page) {
        return new PageResponseDto<F>(page);
    }
}

