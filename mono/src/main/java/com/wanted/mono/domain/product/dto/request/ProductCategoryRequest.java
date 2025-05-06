package com.wanted.mono.domain.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryRequest {
    @JsonProperty("category_id")
    private Long categoryId;
    @JsonProperty("is_primary")
    private Boolean isPrimary;
}