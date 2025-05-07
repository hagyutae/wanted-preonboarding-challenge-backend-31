package com.psh10066.commerce.api.controller;

import com.psh10066.commerce.api.common.ApiResponse;
import com.psh10066.commerce.api.dto.response.GetAllCategoryResponse;
import com.psh10066.commerce.domain.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
