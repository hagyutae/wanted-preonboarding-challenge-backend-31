package com.wanted.mono.domain.category.controller;

import com.wanted.mono.domain.category.entity.dto.CategoryDto;
import com.wanted.mono.domain.category.service.CategoryService;
import com.wanted.mono.global.common.CommonResponse;
import com.wanted.mono.global.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.wanted.mono.global.message.MessageCode.CATEGORY_LIST_SUCCESS;
import static com.wanted.mono.global.message.MessageCode.PRODUCT_CREATE_SUCCESS;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final MessageUtil messageUtil;

    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestParam(value = "level", required = false) Integer level) {
        List<CategoryDto> response = categoryService.findAllByLevel(level);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success(response, messageUtil.get(CATEGORY_LIST_SUCCESS)));
    }
}
