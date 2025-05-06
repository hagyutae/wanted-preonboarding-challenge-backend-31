package com.shopping.mall.main.controller;

import com.shopping.mall.common.response.ApiResponse;
import com.shopping.mall.main.dto.response.MainPageResponse;
import com.shopping.mall.main.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping
    public ResponseEntity<?> getMainPageProducts() {
        MainPageResponse response = mainPageService.getMainPageProducts();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
