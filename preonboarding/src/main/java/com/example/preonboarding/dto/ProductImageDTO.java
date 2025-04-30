package com.example.preonboarding.dto;

import com.example.preonboarding.domain.ProductImages;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductImageDTO {
    private Long id;
    private String url;
    private String altText;
    private boolean isPrimary;
    private int displayOrder;
    @QueryProjection
    public ProductImageDTO(Long productsId, String imageUrl, String imageAltText) {
        this.id = productsId;
        this.url = imageUrl;
        this.altText = imageAltText;
    }

    public ProductImageDTO(ProductImages images) {
        this.id = images.getId();
        this.url = images.getUrl();
        this.altText = images.getAltText();
        this.isPrimary = images.isPrimary();
        this.displayOrder = images.getDisplayOrder();
    }
}
