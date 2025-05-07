package com.psh10066.commerce.api.controller;

import com.psh10066.commerce.api.common.ApiResponse;
import com.psh10066.commerce.api.dto.request.GetCategoryProductsRequest;
import com.psh10066.commerce.api.dto.response.GetAllCategoryResponse;
import com.psh10066.commerce.api.dto.response.GetAllProductsResponse;
import com.psh10066.commerce.api.dto.response.PaginationWithCategoryResponse;
import com.psh10066.commerce.domain.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<List<GetAllCategoryResponse>> getAllCategory(@RequestParam Integer level) {
        List<GetAllCategoryResponse> responses = categoryService.getAllCategory(level);
        return ApiResponse.success(responses, "카테고리 목록을 성공적으로 조회했습니다.");
    }

    @GetMapping("/{id}/products")
    public ApiResponse<PaginationWithCategoryResponse<GetAllProductsResponse>> getCategoryProducts(@PathVariable Long id, GetCategoryProductsRequest request) {
        PaginationWithCategoryResponse<GetAllProductsResponse> response = categoryService.getCategoryProduct(id, request);
        return ApiResponse.success(response, "카테고리 상품 목록을 성공적으로 조회했습니다.");
    }
}
