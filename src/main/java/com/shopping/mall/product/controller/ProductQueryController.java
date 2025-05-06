package com.shopping.mall.product.controller;

import com.shopping.mall.product.dto.request.ProductSearchCondition;
import com.shopping.mall.product.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    @GetMapping
    public ResponseEntity<?> getProducts(ProductSearchCondition condition) {
        return ResponseEntity.ok(productQueryService.getProducts(condition));
    }
}
