package com.wanted.ecommerce.common.response;

import java.util.List;
import org.springframework.data.domain.Page;


public class PaginationResponse<T> extends ApiResponse<PaginationData<T>> {

    private PaginationResponse(PaginationData<T> data, String message) {
        super(data, message);
    }

    public static <T> PaginationResponse<T> of(List<T> items, Page<T> page, String message) {
        Pagination pagination = Pagination.builder()
            .totalItems(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .currentPage(page.getNumber() + 1)
            .perPage(page.getSize())
            .build();

        PaginationData<T> data = PaginationData.of(items, pagination);
        return new PaginationResponse<>(data, message);
    }
}
