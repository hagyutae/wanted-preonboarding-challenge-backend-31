package com.dino.cqrs_challenge.presentation.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "추가 정보")
public class AdditionalInfoDTO {

    @Schema(description = "조립 필요 여부")
    private Boolean assemblyRequired;

    @Schema(description = "조립 시간")
    private String assemblyTime;

}
