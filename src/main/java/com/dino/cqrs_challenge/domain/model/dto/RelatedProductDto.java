package com.dino.cqrs_challenge.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "관련 상품 DTO")
public class RelatedProductDto {

    @Schema(description = "상품 식별 번호")
    private Long id;

    @Schema(description = "상품명")
    private String name;

    @Schema(description = "슬러그")
    private String slug;

    @Schema(description = "짧은 설명")
    private String shortDescription;

    @Schema(description = "메인 이미지")
    private ProductImageSummaryDTO primaryImage;

    @Schema(description = "기본 가격")
    private BigDecimal basePrice;

    @Schema(description = "세일 가격")
    private BigDecimal salePrice;

    @Schema(description = "통화")
    private String currency;
}
