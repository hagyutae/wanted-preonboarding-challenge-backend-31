package com.example.demo.category.controller;

import com.example.demo.category.dto.CategoryPageResponse;
import com.example.demo.category.dto.CategoryQueryFilter;
import com.example.demo.category.dto.CategoryTree;
import com.example.demo.category.service.CategoryService;
import com.example.demo.common.response.ApiResponse;
import com.example.demo.common.util.PageableCreator;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryTree>>> findAllByLevel(@RequestParam(value = "level", required = false) Integer level) {
        CategoryQueryFilter categoryQueryFilter = CategoryQueryFilter.builder()
                .level(level)
                .build();
        List<CategoryTree> categoryTreeList = categoryService.findAllByLevel(categoryQueryFilter);

        return new ResponseEntity<>(
                ApiResponse.success(categoryTreeList, "카테고리 목록을 성공적으로 조회했습니다."),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<ApiResponse<CategoryPageResponse>> findPageByCategoryId(@Positive @PathVariable("id") Long id,
                                                                                  @Positive @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                                                                  @Positive @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer perPage,
                                                                                  @RequestParam(name = "sort", required = false, defaultValue = "created_at:desc") List<String> sort,
                                                                                  @RequestParam(name = "includeSubcategories", required = false, defaultValue = "false") Boolean includeSubcategories) {
        Pageable pageable = PageableCreator.create(page, perPage, sort);
        CategoryQueryFilter categoryQueryFilter = CategoryQueryFilter.builder()
                .parentId(id)
                .build();
        CategoryPageResponse categoryPageResponse = categoryService.findPageByCategory(categoryQueryFilter, includeSubcategories, pageable);

        return new ResponseEntity<>(
                ApiResponse.success(categoryPageResponse, "카테고리 상품 목록을 성공적으로 조회했습니다."),
                HttpStatus.OK
        );
    }
}
