package com.dino.cqrs_challenge.presentation.model.rs;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "상품 옵션 수정 응답 DTO")
public class UpdateProductOptionResponse {

    @Schema(description = "상품 옵션 식별번호")
    private Long id;

    @Schema(description = "상품 옵션 그룹 식별번호")
    private Long optionGroupId;

    @Schema(description = "상품 옵션명")
    private String name;

    @Schema(description = "상품 옵션 추가 가격")
    private BigDecimal additionalPrice;

    @Schema(description = "상품 옵션 SKU")
    private String sku;

    @Schema(description = "상품 옵션 재고")
    private Integer stock;

    @Schema(description = "상품 옵션 노출 순서")
    private Integer displayOrder;
}
