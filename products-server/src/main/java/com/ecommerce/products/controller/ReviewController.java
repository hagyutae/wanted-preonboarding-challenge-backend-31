package com.ecommerce.products.controller;

import com.ecommerce.products.application.dto.PaginationDto;
import com.ecommerce.products.application.dto.ReviewDto;
import com.ecommerce.products.application.service.ReviewService;
import com.ecommerce.products.controller.dto.ReviewCreateRequest;
import com.ecommerce.products.controller.dto.ReviewUpdateRequest;
import com.ecommerce.products.controller.mapper.ReviewControllerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewControllerMapper mapper;

    public ReviewController(
            ReviewService reviewService,
            ReviewControllerMapper mapper
    ) {
        this.reviewService = reviewService;
        this.mapper = mapper;
    }

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<?> getProductReviews(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage,
            @RequestParam(defaultValue = "created_at:desc") String sort,
            @RequestParam(required = false) Integer rating) {

        var paginationRequest = PaginationDto.PaginationRequest.builder()
                .page(page)
                .size(perPage)
                .sort(sort)
                .build();

        // 서비스 호출
        ReviewDto.ReviewPage reviewsPage = reviewService.getProductReviews(productId, rating, paginationRequest);

        return APIResponse.success(reviewsPage, "상품 리뷰를 성공적으로 조회했습니다.").build();
    }

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<?> createReview(
            @PathVariable Long productId,
            @RequestBody ReviewCreateRequest request) {

        // 인증 관련 로직 (실제로는 Spring Security 등을 통해 구현)
        Long userId = 1L; // 임시로 고정된 사용자 ID 사용

        // DTO 변환
        ReviewDto.CreateRequest serviceDto = mapper.toReviewDtoCreateRequest(request);

        // 서비스 호출
        ReviewDto.Review response = reviewService.createReview(productId, userId, serviceDto);

        return APIResponse.success(response, "리뷰가 성공적으로 등록되었습니다.").build(HttpStatus.CREATED);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<?> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequest request) {

        // 인증 관련 로직 (실제로는 Spring Security 등을 통해 구현)
        Long userId = 1L; // 임시로 고정된 사용자 ID 사용

        // DTO 변환
        ReviewDto.UpdateRequest serviceDto = mapper.toReviewDtoUpdateRequest(request);

        // 서비스 호출
        ReviewDto.Review response = reviewService.updateReview(reviewId, userId, serviceDto);

        return APIResponse.success(response, "리뷰가 성공적으로 수정되었습니다.").build();
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {

        // 인증 관련 로직 (실제로는 Spring Security 등을 통해 구현)
        Long userId = 1L; // 임시로 고정된 사용자 ID 사용

        // 서비스 호출
        reviewService.deleteReview(reviewId, userId);

        return APIResponse.success(null, "리뷰가 성공적으로 삭제되었습니다.").build();
    }
}