package com.example.demo.product.controller;

import com.example.demo.common.response.ApiResponse;
import com.example.demo.product.dto.MainProductSummary;
import com.example.demo.product.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainPageController {
    private final MainService mainService;

    @GetMapping
    public ResponseEntity<ApiResponse<MainProductSummary>> findMainProducts() {
        MainProductSummary mainProductSummary = mainService.search();
        return new ResponseEntity<>(
                ApiResponse.success(mainProductSummary, "메인 페이지 상품 목록을 성공적으로 조회했습니다."),
                HttpStatus.OK
        );
    }
}
