package com.wanted.ecommerce.category.controller;

import com.wanted.ecommerce.category.dto.request.PageableRequest;
import com.wanted.ecommerce.category.dto.response.CategoriesResponse;
import com.wanted.ecommerce.category.dto.response.CategoryProductListResponse;
import com.wanted.ecommerce.category.service.CategoryService;
import com.wanted.ecommerce.common.constants.MessageConstants;
import com.wanted.ecommerce.common.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{level}")
    public ResponseEntity<ApiResponse<List<CategoriesResponse>>> getAllCategories(
        @PathVariable int level) {
        List<CategoriesResponse> responses = categoryService.getAllCategoryByLevel(level);
        return ResponseEntity.ok(
            ApiResponse.success(responses, MessageConstants.FUNDED_CATEGORIES.getMessage()));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<ApiResponse<CategoryProductListResponse>> getCategoryProductList(
        @PathVariable Long id,
        @Valid @ModelAttribute PageableRequest pageableRequest,
        @RequestParam("includeSubcategories") Boolean includeSubcategories) {

        CategoryProductListResponse response = categoryService.getCategoryProducts(id,
            includeSubcategories, pageableRequest);
        return ResponseEntity.ok(
            ApiResponse.success(response, MessageConstants.FUNDED_CATEGORY_PRODUCTS.getMessage()));
    }
}
