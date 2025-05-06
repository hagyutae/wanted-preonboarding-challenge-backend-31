package com.shopping.mall.product.controller;

import com.shopping.mall.common.response.ApiResponse;
import com.shopping.mall.product.dto.response.ProductDetailResponse;
import com.shopping.mall.product.dto.request.ProductSearchCondition;
import com.shopping.mall.product.service.ProductDetailQueryService;
import com.shopping.mall.product.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductQueryController {

    private final ProductQueryService productQueryService;
    private final ProductDetailQueryService productDetailQueryService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetail(@PathVariable Long id) {
        ProductDetailResponse response = productDetailQueryService.getProductDetail(id);
        return ResponseEntity.ok(ApiResponse.success(response, "상품 상세 정보를 성공적으로 조회했습니다."));
    }

    @GetMapping
    public ResponseEntity<?> getProducts(ProductSearchCondition condition) {
        return ResponseEntity.ok(ApiResponse.success(productQueryService.getProducts(condition), "상품 목록을 성공적으로 조회했습니다."));
    }
}
