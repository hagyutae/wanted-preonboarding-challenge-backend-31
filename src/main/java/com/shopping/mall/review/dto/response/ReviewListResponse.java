package com.shopping.mall.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReviewListResponse {

    private List<ReviewResponse> items;

    private ReviewSummaryResponse summary;

    private PaginationResponse pagination;

    @Getter
    @Builder
    public static class PaginationResponse {
        private Long totalItems;
        private Integer totalPages;
        private Integer currentPage;
        private Integer perPage;
    }
}