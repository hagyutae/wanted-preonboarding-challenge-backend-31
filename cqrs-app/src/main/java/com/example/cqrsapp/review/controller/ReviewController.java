package com.example.cqrsapp.review.controller;

import com.example.cqrsapp.common.response.APIDataResponse;
import com.example.cqrsapp.review.dto.request.RegisterReviewDto;
import com.example.cqrsapp.review.dto.request.UpdateReviewDto;
import com.example.cqrsapp.review.dto.response.RegisterReviewResponseDto;
import com.example.cqrsapp.review.dto.response.ReviewListResponse;
import com.example.cqrsapp.review.dto.response.UpdateReviewResponseDto;
import com.example.cqrsapp.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/products/{productId}/reviews")
    APIDataResponse<ReviewListResponse> searchReviews(
            @PathVariable("productId") Long productId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam("rating") Integer rating) {
        ReviewListResponse reviews = reviewService.searchReview(productId, rating, pageable);
        return APIDataResponse.success(reviews, "상품 리뷰를 성공적으로 조회했습니다.");
    }

    @PostMapping("/products/{productId}/reviews")
    APIDataResponse<RegisterReviewResponseDto> createReview(@PathVariable("productId") Long productId,
                                                            @RequestBody RegisterReviewDto dto)
    {
        //TODO : 인증 기능을 추가 하지 않아서 user id 하드코딩
        RegisterReviewResponseDto result = reviewService.createReview(1L, productId,  dto);
        return APIDataResponse.success(result, "리뷰가 성공적으로 등록되었습니다.");
    }

    @PostMapping("/reviews/{reviewId}")
    APIDataResponse<UpdateReviewResponseDto> updateReview(@PathVariable("reviewId") Long reviewId, @RequestBody UpdateReviewDto dto) {
        UpdateReviewResponseDto result = reviewService.updateReview(reviewId, dto);
        return APIDataResponse.success(result, "리뷰가 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/reviews/{reviewId}")
    APIDataResponse<Void> deleteReview(@PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return APIDataResponse.success("리뷰가 성공적으로 삭제되었습니다.");
    }

}
