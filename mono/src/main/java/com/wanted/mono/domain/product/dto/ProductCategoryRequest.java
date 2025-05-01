package com.wanted.mono.domain.product.dto;

import lombok.Data;

@Data
public class ProductCategoryRequest {
    private Long categoryId;
    private Boolean isPrimary;
}