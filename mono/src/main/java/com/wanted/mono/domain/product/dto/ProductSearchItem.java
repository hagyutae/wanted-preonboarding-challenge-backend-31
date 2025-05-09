package com.wanted.mono.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted.mono.domain.product.dto.model.ProductImageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchItem {
    private Long id;

    private String name;

    private String slug;

    @JsonProperty("short_description")
    private String shortDescription;

    @JsonProperty("base_price")
    private BigDecimal basePrice;

    @JsonProperty("sale_price")
    private BigDecimal salePrice;

    private String currency;

    @JsonProperty("primary_image")
    private ProductImageSearchDto primaryImage;

    private BrandAndSellerItem brand;

    private BrandAndSellerItem seller;

    private Double rating;

    @JsonProperty("review_count")
    private Long reviewCount;

    @JsonProperty("in_stock")
    private boolean inStock;

    private String status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;



    // -------------------------

}
