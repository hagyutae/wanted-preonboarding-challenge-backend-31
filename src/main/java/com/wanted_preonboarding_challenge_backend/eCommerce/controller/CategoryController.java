package com.wanted_preonboarding_challenge_backend.eCommerce.controller;

import com.wanted_preonboarding_challenge_backend.eCommerce.dto.category.response.CategoryTreeResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request.ProductsByCategoryCondition;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.response.ProductListResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.service.CategoryService;
import com.wanted_preonboarding_challenge_backend.eCommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.common.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryTreeResponse>>> getCategories(
            @RequestParam(defaultValue = "1") int level
    ) {
        List<CategoryTreeResponse> response = categoryService.getCategoryTree(level);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(response, "카테고리 목록을 성공적으로 조회했습니다."));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<ApiResponse<ProductListResponse>> getProductsByCategory(
            @PathVariable Long id,
            @ModelAttribute ProductsByCategoryCondition condition) {

        ProductListResponse response = productService.getProductListByCategoryId(id, condition);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(response, "카테고리 상품 목록을 성공적으로 조회했습니다."));
    }

}
