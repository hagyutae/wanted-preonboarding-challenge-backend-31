package com.shopping.mall.product.controller;

import com.shopping.mall.product.dto.response.ProductOptionGroupResponse;
import com.shopping.mall.product.service.ProductOptionGroupQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/option-groups")
@RequiredArgsConstructor
public class ProductOptionGroupController {

    private final ProductOptionGroupQueryService productOptionGroupQueryService;

    @GetMapping
    public List<ProductOptionGroupResponse> getOptionGroups(@PathVariable Long productId) {
        return productOptionGroupQueryService.getOptionGroups(productId);
    }
}