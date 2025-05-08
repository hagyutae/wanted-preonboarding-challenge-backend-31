package com.psh10066.commerce.api.controller;

import com.psh10066.commerce.api.common.ApiResponse;
import com.psh10066.commerce.api.dto.request.CreateReviewRequest;
import com.psh10066.commerce.api.dto.request.GetProductReviewsRequest;
import com.psh10066.commerce.api.dto.response.CreateReviewResponse;
import com.psh10066.commerce.api.dto.response.GetAllReviewsResponse;
import com.psh10066.commerce.api.dto.response.PaginationWithSummaryResponse;
import com.psh10066.commerce.domain.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/products/{id}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateReviewResponse> createReview(
        @PathVariable Long id,
        @Valid @RequestBody CreateReviewRequest request
    ) {
        CreateReviewResponse response = reviewService.createReview(id, request);
        return ApiResponse.success(response, "리뷰가 성공적으로 등록되었습니다.");
    }
}
