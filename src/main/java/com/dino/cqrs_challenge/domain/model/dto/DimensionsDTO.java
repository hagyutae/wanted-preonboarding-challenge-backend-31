package com.dino.cqrs_challenge.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "제품 크기")
public class DimensionsDTO {

    @Schema(description = "너비")
    private Integer width;

    @Schema(description = "높이")
    private Integer height;

    @Schema(description = "깊이")
    private Integer depth;
}
