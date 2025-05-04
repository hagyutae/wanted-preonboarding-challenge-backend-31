package com.wanted.mono.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRelatedDto {
    private Long id;
    private String name;
    private String slug;
    @JsonProperty("short_description")
    private String shortDescription;

    @JsonProperty("primary_image")
    private ProductImageSearchDto primaryImage;

    @JsonProperty("base_price")
    private BigDecimal basePrice;

    @JsonProperty("sale_price")
    private BigDecimal salePrice;

    private String currency;

}
