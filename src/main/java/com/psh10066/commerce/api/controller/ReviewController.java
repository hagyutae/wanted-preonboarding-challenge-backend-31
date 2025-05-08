package com.psh10066.commerce.api.controller;

import com.psh10066.commerce.api.common.ApiResponse;
import com.psh10066.commerce.api.dto.request.GetProductReviewsRequest;
import com.psh10066.commerce.api.dto.response.GetAllReviewsResponse;
import com.psh10066.commerce.api.dto.response.PaginationWithSummaryResponse;
import com.psh10066.commerce.domain.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/products/{id}/reviews")
    public ApiResponse<PaginationWithSummaryResponse<GetAllReviewsResponse>> getProductReviews(
        @PathVariable Long id,
        GetProductReviewsRequest request
    ) {
        PaginationWithSummaryResponse<GetAllReviewsResponse> response = reviewService.getProductReviews(id, request);
        return ApiResponse.success(response, "상품 리뷰를 성공적으로 조회했습니다.");
    }
}
