package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.Product;
import com.dino.cqrs_challenge.domain.entity.ProductImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "상품 이미지")
public class SaveProductImageDTO {

    @Schema(description = "이미지 URL")
    private String url;

    @Schema(description = "이미지 설명")
    private String altText;

    @Schema(description = "대표 이미지 여부")
    private Boolean isPrimary;

    @Schema(description = "표시 순서")
    private Integer displayOrder;

    @Schema(description = "옵션 ID (해당 이미지가 특정 옵션에 속하는 경우)")
    private Long optionId;

    public ProductImage toEntity(Product product) {
        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setUrl(this.url);
        productImage.setAltText(this.altText);
        productImage.setIsPrimary(this.isPrimary);
        productImage.setDisplayOrder(this.displayOrder);
        return productImage;
    }
}
