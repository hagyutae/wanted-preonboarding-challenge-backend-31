package com.dino.cqrs_challenge.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "가격 정보")
public class SaveProductPriceDTO {

    @Schema(description = "기본 가격")
    private Integer basePrice;

    @Schema(description = "할인가")
    private Integer salePrice;

    @Schema(description = "원가")
    private Integer costPrice;

    @Schema(description = "통화")
    private String currency;

    @Schema(description = "세율(%)")
    private Integer taxRate;

}
