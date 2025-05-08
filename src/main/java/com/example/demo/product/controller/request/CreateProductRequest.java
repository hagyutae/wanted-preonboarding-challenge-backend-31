package com.example.demo.product.controller.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateProductRequest(
        String name,
        String slug,
        String shortDescription,
        String fullDescription,
        Long sellerId,
        Long brandId,
        String status,
        ProductDetailRequest detail,
        ProductPriceRequest price,
        List<ProductCategoryRequest> categories,
        List<ProductOptionGroupRequest> optionGroups,
        List<ProductImageRequest> images,
        List<Long> tags
) { }
