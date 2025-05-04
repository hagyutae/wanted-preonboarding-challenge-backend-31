package com.ecommerce.products.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaginationRequest {
        private int page;
        private int size;
        private String sort = "created_at:desc";

        public Pageable toPageable() {
            return PageRequest.of(
                page - 1,
                size,
                Utils.createBasicSortBySortParams(sort)
            );
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaginationInfo {
        private Integer totalItems;
        private Integer totalPages;
        private Integer currentPage;
        private Integer perPage;
    }
}
