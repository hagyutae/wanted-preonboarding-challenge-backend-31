package com.dino.cqrs_challenge.presentation.model.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Schema(description = "상품 옵션 수정 요청 DTO")
public class UpdateProductOptionRequest {

    @Schema(description = "상품 옵션명")
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
