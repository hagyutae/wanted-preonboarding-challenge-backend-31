package com.wanted.mono.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted.mono.domain.product.entity.Product;
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

    // -------------------

    public static ProductRelatedDto of(Product product, String url, String altText, BigDecimal basePrice, BigDecimal salePrice, String currency) {
        ProductRelatedDto dto = new ProductRelatedDto();
        dto.id = product.getId();
        dto.name = product.getName();
        dto.slug = product.getSlug();
        dto.shortDescription = product.getShortDescription();
        dto.primaryImage = new ProductImageSearchDto(url, altText);
        dto.basePrice = basePrice;
        dto.salePrice = salePrice;
        dto.currency = currency;
        return dto;
    }

}
