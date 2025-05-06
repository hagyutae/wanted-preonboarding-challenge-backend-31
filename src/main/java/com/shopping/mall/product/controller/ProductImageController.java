package com.shopping.mall.product.controller;

import com.shopping.mall.product.dto.request.ProductImageCreateRequest;
import com.shopping.mall.product.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping("/{productId}/images")
    public ResponseEntity<?> addImage(
            @PathVariable Long productId,
            @RequestBody ProductImageCreateRequest request) {

        productImageService.addImage(productId, request);
        return ResponseEntity.ok().build();
    }
}
