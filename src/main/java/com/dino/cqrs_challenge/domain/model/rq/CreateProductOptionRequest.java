package com.dino.cqrs_challenge.domain.model.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Schema(description = "상품 옵션 추가 요청 DTO")
public class CreateProductOptionRequest {

    @Schema(description = "상품 옵션 그룹 식별 번호")
    private Long optionGroupId;

    @Schema(description = "상품 옵션 이름")
    private String name;

    @Schema(description = "추가 금액")
    private BigDecimal additionalPrice;

    @Schema(description = "SKU 코드")
    private String sku;

    @Schema(description = "재고 수량")
    private Integer stock;

    @Schema(description = "표시 순서")
    private Integer displayOrder;

}
