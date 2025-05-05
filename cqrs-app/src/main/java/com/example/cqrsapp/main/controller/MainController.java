package com.example.cqrsapp.main.controller;

import com.example.cqrsapp.common.response.APIDataResponse;
import com.example.cqrsapp.main.dto.MainResponse;
import com.example.cqrsapp.main.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    @GetMapping
    APIDataResponse<MainResponse> searchProducts()
    {
        MainResponse products = mainService.getMainPageContent();
        return APIDataResponse.success(products, "메인 페이지 상품 목록을 성공적으로 조회했습니다.");
    }
}
