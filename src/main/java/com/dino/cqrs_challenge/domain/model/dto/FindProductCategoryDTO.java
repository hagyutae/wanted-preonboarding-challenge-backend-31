package com.dino.cqrs_challenge.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "카테고리 정보 DTO")
public class FindProductCategoryDTO {
    @Schema(description = "카테고리 식별자")
    private Long id;

    @Schema(description = "카테고리 이름")
    private String name;

    @Schema(description = "슬러그")
    private String slug;

    @Schema(description = "대표 카테고리 여부")
    private Boolean isPrimary;

    @Schema(description = "부모 카테고리")
    private FindProductCategoryDTO parentCategory;
}
