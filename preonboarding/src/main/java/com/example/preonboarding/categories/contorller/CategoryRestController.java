package com.example.preonboarding.categories.contorller;

import com.example.preonboarding.categories.response.CategoriesResponse;
import com.example.preonboarding.categories.service.CategoriesService;
import com.example.preonboarding.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryRestController {
    private final CategoriesService categoriesService;
    @GetMapping(value = "/categories")
    public ResponseEntity<CommonResponse> findCategories(@RequestParam("level") Integer level){
        if(level == null) level = 1;

        List<CategoriesResponse> categories = categoriesService.findCategories(level);

        return ResponseEntity.ok().body(CommonResponse.success(categories,"카테고리 목록을 성공적으로 조회했습니다."));
    }
}
