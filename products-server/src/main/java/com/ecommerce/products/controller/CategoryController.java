package com.ecommerce.products.controller;

import com.ecommerce.products.application.dto.CategoryDto;
import com.ecommerce.products.application.dto.PaginationDto;
import com.ecommerce.products.application.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<APIResponse<List<CategoryDto.Category>>> getAllCategories(@RequestParam(required = false) Integer level) {
        return APIResponse.success(
                categoryService.getAllCategories(level),
                "카테고리 목록을 성공적으로 조회했습니다."
        ).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryDto.Category>> getCategoryById(@PathVariable Long id) {
        CategoryDto.Category category = categoryService.getCategoryById(id);
        return  APIResponse.success(
                category,
                "카테고리 정보를 성공적으로 조회했습니다."
        ).build();
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<APIResponse<CategoryDto.CategoryProducts>> getCategoryProducts(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage,
            @RequestParam(defaultValue = "created_at:desc") String sort,
            @RequestParam(defaultValue = "true") Boolean includeSubcategories) {

        var paginationRequest = PaginationDto.PaginationRequest.builder()
                .page(page)
                .size(perPage)
                .sort(sort)
                .build();

        // 서비스 호출
        CategoryDto.CategoryProducts response =
                categoryService.getCategoryProducts(id, includeSubcategories, paginationRequest);

        return APIResponse.success(
                response,
                "카테고리 상품 목록을 성공적으로 조회했습니다."
        ).build();
    }
}