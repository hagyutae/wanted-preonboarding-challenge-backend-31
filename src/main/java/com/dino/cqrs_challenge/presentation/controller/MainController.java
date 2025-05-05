package com.dino.cqrs_challenge.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "메인 페이지 관리", description = "메인 페이지 관리 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {

    @Operation(summary = "메인 페이지용 상품 목록 조회", description = "신규 상품 + 카테고리별 인기 상품 순의 상품 목록 조회")
    @GetMapping("/main")
    public void getMainPageProducts() {
        // TODO 메인 페이지 상품 목록 조회 로직 구현
    }
}
