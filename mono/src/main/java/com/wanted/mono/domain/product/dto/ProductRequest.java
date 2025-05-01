package com.wanted.mono.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    private String name;
    private String slug;
    @JsonProperty("short_description")
    private String shortDescription;
    @JsonProperty("full_description")
    private String fullDescription;
    @JsonProperty("seller_id")
    private Long sellerId;
    @JsonProperty("brand_id")
    private Long brandId;
    private String status;

    private ProductDetailRequest detail;
    private ProductPriceRequest price;

    private List<ProductCategoryRequest> categories;
    @JsonProperty("option_groups")
    private List<ProductOptionGroupRequest> optionGroups;
    private List<ProductImageRequest> images;

    private List<Long> tags;
}
