package com.psh10066.commerce.api.controller;

import com.psh10066.commerce.api.common.ApiResponse;
import com.psh10066.commerce.api.dto.response.GetMainResponse;
import com.psh10066.commerce.domain.service.MainService;
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
    public ApiResponse<GetMainResponse> getMain() {
        GetMainResponse response = mainService.getMain();
        return ApiResponse.success(response, "메인 페이지 상품 목록을 성공적으로 조회했습니다.");
    }
}
