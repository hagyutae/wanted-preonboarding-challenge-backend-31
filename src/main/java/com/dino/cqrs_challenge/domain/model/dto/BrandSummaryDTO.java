package com.dino.cqrs_challenge.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "브랜드 요약 정보 DTO")
public class BrandSummaryDTO {

    @Schema(description = "브랜드 식별번호")
    private Long id;

    @Schema(description = "브랜드명")
    private String name;

}
