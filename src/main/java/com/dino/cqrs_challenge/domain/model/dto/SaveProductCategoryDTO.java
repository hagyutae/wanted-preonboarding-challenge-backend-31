package com.dino.cqrs_challenge.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "카테고리 정보")
public class SaveProductCategoryDTO {

    @Schema(description = "카테고리 ID")
    private Long categoryId;

    @Schema(description = "대표 카테고리 여부")
    private Boolean isPrimary;
}
