package com.preonboarding.controller;

import com.preonboarding.dto.request.ProductCreateRequestDto;
import com.preonboarding.dto.response.ProductResponse;
import com.preonboarding.global.response.BaseResponse;
import com.preonboarding.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<BaseResponse<ProductResponse>> addProduct(@RequestBody ProductCreateRequestDto dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }
}
