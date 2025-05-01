package com.dino.cqrs_challenge.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "브랜드 정보")
public class FindBrandDTO {

    @Schema(description = "브랜드 식별번호")
    private Long id;

    @Schema(description = "브랜드명")
    private String name;

    @Schema(description = "브랜드 설명")
    private String description;

    @Schema(description = "브랜드 로고 URL")
    private String logoUrl;

    @Schema(description = "브랜드 웹사이트 URL")
    private String website;
}
