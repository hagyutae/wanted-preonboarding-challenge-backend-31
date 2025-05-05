package com.dino.cqrs_challenge.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "리뷰", description = "리뷰 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    @Operation(summary = "상품 리뷰 조회", description = "상품 ID를 기반으로 해당 상품의 리뷰 목록 조회")
    @GetMapping("/products/{id}/reviews")
    public void getReviews(@PathVariable Integer id) {
        // TODO 리뷰 목록 조회 로직 구현
    }

    @Operation(summary = "리뷰 작성", description = "상품 ID를 기반으로 리뷰 작성")
    @PostMapping("/products/{id}/reviews")
    public void createReview(@PathVariable Integer id) {
        // TODO 리뷰 작성 로직 구현
    }

    @Operation(summary = "리뷰 수정", description = "리뷰 ID를 기반으로 리뷰 수정")
    @PutMapping("/reviews/{id}")
    public void updateReview(@PathVariable Integer id) {
        // TODO 리뷰 수정 로직 구현
    }

    @Operation(summary = "리뷰 삭제", description = "리뷰 ID를 기반으로 리뷰 삭제")
    @DeleteMapping("/reviews/{id}")
    public void deleteReview(@PathVariable Integer id) {
        // TODO 리뷰 삭제 로직 구현
    }

}
