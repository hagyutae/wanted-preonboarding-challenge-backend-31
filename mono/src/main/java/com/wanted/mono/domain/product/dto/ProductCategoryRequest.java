package com.wanted.mono.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductCategoryRequest {
    @JsonProperty("category_id")
    private Long categoryId;
    @JsonProperty("is_primary")
    private Boolean isPrimary;
}