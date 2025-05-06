package com.shopping.mall.review.controller;

import com.shopping.mall.common.response.ApiResponse;
import com.shopping.mall.review.dto.request.ReviewCreateRequest;
import com.shopping.mall.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<ApiResponse<?>> createReview(
            @PathVariable Long productId,
            @RequestBody ReviewCreateRequest request) {

        return ResponseEntity.ok(reviewService.createReview(productId, request));
    }
}