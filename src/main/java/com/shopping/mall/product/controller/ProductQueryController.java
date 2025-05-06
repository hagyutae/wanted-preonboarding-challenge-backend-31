package com.shopping.mall.product.controller;

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
    public ProductDetailResponse getProductDetail(@PathVariable Long id) {
        return productDetailQueryService.getProductDetail(id);
    }

    @GetMapping
    public ResponseEntity<?> getProducts(ProductSearchCondition condition) {
        return ResponseEntity.ok(productQueryService.getProducts(condition));
    }
}
