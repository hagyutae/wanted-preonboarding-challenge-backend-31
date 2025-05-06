package com.shopping.mall.product.controller;

import com.shopping.mall.product.dto.request.ProductTagCreateRequest;
import com.shopping.mall.product.service.ProductTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductTagController {

    private final ProductTagService productTagService;

    @PostMapping("/{productId}/tags")
    public ResponseEntity<?> addTags(
            @PathVariable Long productId,
            @RequestBody ProductTagCreateRequest request) {

        productTagService.addTags(productId, request);
        return ResponseEntity.ok().build();
    }
}
