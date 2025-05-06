package com.shopping.mall.product.controller;

import com.shopping.mall.common.response.ApiResponse;
import com.shopping.mall.product.dto.request.ProductCreateRequest;
import com.shopping.mall.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductCreateRequest request) {
        Long productId = productService.createProduct(request);
        return ResponseEntity.ok(ApiResponse.success(productId, "상품이 성공적으로 등록되었습니다."));
    }
}
