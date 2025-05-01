package com.wanted.mono.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private Long sellerId;
    private Long brandId;
    private String status;

    private ProductDetailRequest detail;
    private ProductPriceRequest price;

    private List<ProductCategoryRequest> categories;
    private List<ProductOptionGroupRequest> optionGroups;
    private List<ProductImageRequest> images;

    private List<Long> tags;
}
