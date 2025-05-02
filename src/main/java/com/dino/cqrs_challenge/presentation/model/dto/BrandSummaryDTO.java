package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.Brand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "브랜드 요약 정보 DTO")
public class BrandSummaryDTO {

    @Schema(description = "브랜드 식별번호")
    private Long id;

    @Schema(description = "브랜드명")
    private String name;

    public static BrandSummaryDTO from(Brand brand) {
        if (brand == null) {
            return null;
        }
        BrandSummaryDTO dto = new BrandSummaryDTO();
        dto.id = brand.getId();
        dto.name = brand.getName();
        return dto;
    }
}
