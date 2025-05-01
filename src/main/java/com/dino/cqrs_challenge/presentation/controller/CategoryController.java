package com.dino.cqrs_challenge.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카테고리 관리", description = "카테고리 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    @Operation(summary = "카테고리 목록 조회", description = "계층 구조를 포함한 카테고리 목록 조회")
    @GetMapping("/categories")
    public void getCategories() {
        // TODO 카테고리 목록 조회 로직 구현
    }

    @Operation(summary = "특정 카테고리의 상품 목록 조회", description = "카테고리 ID를 기반으로 해당 카테고리에 속한 상품 목록 조회")
    @GetMapping("/categories/{id}/products")
    public void getProductsByCategory(@PathVariable Integer id) {
        // TODO 특정 카테고리의 상품 목록 조회 로직 구현
    }
}
