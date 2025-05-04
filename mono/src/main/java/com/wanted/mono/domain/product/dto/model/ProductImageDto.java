package com.wanted.mono.domain.product.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted.mono.domain.product.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDto {
    private Long id;
    private String url;
    @JsonProperty("alt_text")
    private String altText;
    @JsonProperty("is_primary")
    private boolean isPrimary;
    @JsonProperty("display_order")
    private Integer displayOrder;
    @JsonProperty("option_id")
    private Long optionId;

    // --------------
    public static ProductImageDto of(ProductImage productImage) {
        ProductImageDto dto = new ProductImageDto();
        dto.id = productImage.getId();
        dto.url = productImage.getUrl();
        dto.altText = productImage.getAltText();
        dto.isPrimary = productImage.getIsPrimary();
        dto.displayOrder = productImage.getDisplayOrder();
        dto.optionId = productImage.getOption().getId();
        return dto;
    }
}
