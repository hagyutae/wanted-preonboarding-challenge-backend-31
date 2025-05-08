package com.example.demo.product.dto;

import com.example.demo.product.entity.ProductStatus;
import lombok.Builder;

import java.util.Collection;

@Builder
public record ProductQueryFilter(
        ProductStatus status,
        Integer minPrice,
        Integer maxPrice,
        Collection<Long> categoryIds,
        Long seller,
        Long brand,
        Boolean inStock,
        String search
) {
}
