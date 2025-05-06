package com.shopping.mall.product.controller;

import com.shopping.mall.product.dto.request.ProductOptionGroupCreateRequest;
import com.shopping.mall.product.service.ProductOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    @PostMapping("/{productId}/options")
    public ResponseEntity<?> addOptionGroup(
            @PathVariable Long productId,
            @RequestBody ProductOptionGroupCreateRequest request) {

        productOptionService.addOptionGroup(productId, request);
        return ResponseEntity.ok().build();
    }
}
