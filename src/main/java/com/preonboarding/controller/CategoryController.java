package com.preonboarding.controller;

import com.preonboarding.dto.response.category.CategoryResponse;
import com.preonboarding.global.response.BaseResponse;
import com.preonboarding.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
