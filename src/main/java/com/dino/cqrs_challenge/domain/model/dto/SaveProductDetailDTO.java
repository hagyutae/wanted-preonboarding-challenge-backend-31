package com.dino.cqrs_challenge.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Schema(description = "상품 상세 정보")
public class SaveProductDetailDTO {

    @Schema(description = "무게(kg)")
    private BigDecimal weight;

    @Schema(description = "제품 크기")
    private DimensionsDTO dimensions;

    @Schema(description = "소재")
    private String materials;

    @Schema(description = "원산지")
    private String countryOfOrigin;

    @Schema(description = "품질 보증 정보")
    private String warrantyInfo;

    @Schema(description = "관리 방법")
    private String careInstructions;

    @Schema(description = "추가 정보")
    private AdditionalInfoDTO additionalInfo;

}
