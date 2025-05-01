package com.dino.cqrs_challenge.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "상품 태그")
public class FindProductTagDTO {

    @Schema(description = "태그 식별 번호")
    private Long id;

    @Schema(description = "태그 이름")
    private String name;

    @Schema(description = "슬러그")
    private String slug;
}
