package com.dino.cqrs_challenge.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "상품 옵션")
public class SaveProductOptionDTO {

    @Schema(description = "옵션명")
    private String name;

    @Schema(description = "추가 금액")
    private Integer additionalPrice;

    @Schema(description = "SKU 코드")
    private String sku;

    @Schema(description = "재고 수량")
    private Integer stock;

    @Schema(description = "표시 순서")
    private Integer displayOrder;
}
