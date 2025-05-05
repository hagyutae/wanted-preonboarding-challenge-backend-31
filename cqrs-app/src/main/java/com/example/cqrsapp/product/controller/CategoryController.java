package com.example.cqrsapp.product.controller;

import com.example.cqrsapp.product.dto.response.CategoryProductListResponse;
import com.example.cqrsapp.product.dto.response.CategoryResponse;
import com.example.cqrsapp.common.response.APIDataResponse;
import com.example.cqrsapp.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    APIDataResponse<List<CategoryResponse>> getCategoriesByLevel(@RequestParam("level") int level) {
        List<CategoryResponse> categories = categoryService.getCategoriesByLevel(level);
        return APIDataResponse.success(categories, "카테고리 목록을 성공적으로 조회했습니다.");
    }

    @GetMapping("/{categoryId}/products")
    APIDataResponse<CategoryProductListResponse> getCategoriesProductList(@PathVariable("categoryId") Long categoryId,
                                                                                @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                                @RequestParam("includeSubcategories") Boolean includeSubcategories

    ) {
        CategoryProductListResponse categories = categoryService.getCategoriesProduct(categoryId,includeSubcategories, pageable);
        return APIDataResponse.success(categories, "카테고리 상품 목록을 성공적으로 조회했습니다.");
    }
}

