package com.example.demo.productoption.dto;

import com.querydsl.core.annotations.QueryProjection;

public record ProductStock(
        Long productId,
        int stockCount
) {
    @QueryProjection
    public ProductStock {

    }
}
