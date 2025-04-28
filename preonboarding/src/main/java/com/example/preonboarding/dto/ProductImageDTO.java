package com.example.preonboarding.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductImageDTO {
    private Long productsId;
    private String imageUrl;
    private String imageAltText;

    @QueryProjection
    @Builder
    public ProductImageDTO(Long productsId, String imageUrl, String imageAltText) {
        this.productsId = productsId;
        this.imageUrl = imageUrl;
        this.imageAltText = imageAltText;
    }
}
