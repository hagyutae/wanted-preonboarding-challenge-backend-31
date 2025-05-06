package com.sandro.wanted_shop.mainpage;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/main")
@RestController
public class MainPageController {
    /*
     - 인기 상품 (판매량, 조회수 기준)
     - 신규 상품 (등록일 기준)
     - 카테고리 목록
     */
    @GetMapping
    public void mainPage() {
    }
}
