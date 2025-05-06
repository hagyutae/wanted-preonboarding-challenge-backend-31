package com.shopping.mall.product.controller;

import com.shopping.mall.common.response.ApiResponse;
import com.shopping.mall.product.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<?> getTags() {
        return ResponseEntity.ok(ApiResponse.success(tagService.getAllTags(), "태그 목록을 성공적으로 조회했습니다."));
    }
}
