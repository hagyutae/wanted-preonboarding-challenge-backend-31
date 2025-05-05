package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.ProductImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "상품 이미지 요약 정보 조회 DTO")
public class ProductImageSummaryDTO {

    @Schema(description = "이미지 URL")
    private String url;

    @Schema(description = "이미지 대체 텍스트")
    private String altText;

    public static ProductImageSummaryDTO from(ProductImage primaryImage) {
        if (primaryImage == null) {
            return null;
        }
        ProductImageSummaryDTO dto = new ProductImageSummaryDTO();
        dto.url = primaryImage.getUrl();
        dto.altText = primaryImage.getAltText();
        return dto;
    }
}
