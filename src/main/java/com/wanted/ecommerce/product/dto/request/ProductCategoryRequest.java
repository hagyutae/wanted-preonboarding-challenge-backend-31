package com.wanted.ecommerce.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryRequest {
    private Integer categoryId;
    private Boolean isPrimary;
}
