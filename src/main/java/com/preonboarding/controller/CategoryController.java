package com.preonboarding.controller;

import com.preonboarding.dto.response.category.CategoryResponse;
import com.preonboarding.dto.response.product.ProductPageResponse;
import com.preonboarding.global.response.BaseResponse;
import com.preonboarding.global.response.paging.CategoryPageBaseResponse;
import com.preonboarding.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/categories")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<CategoryResponse>>> getCategories(@RequestParam int level) {
        return ResponseEntity.ok(categoryService.getCategories(level));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<CategoryPageBaseResponse<ProductPageResponse>> getProductsByCategory(@PathVariable Long id,
                                                                                               @RequestParam(required = false) Integer page,
                                                                                               @RequestParam(required = false) Integer perPage,
                                                                                               @RequestParam(required = false) String sort) {
        return ResponseEntity.ok(categoryService.getProductsByCategory(id,page,perPage,sort));
    }
}
