package com.shopping.mall.product.dto.request;

import com.shopping.mall.product.entity.ProductStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequest {
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private ProductStatus status;
}