package com.wanted_preonboarding_challenge_backend.eCommerce.dto.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DimensionsDto {
    @JsonProperty("width")
    private Integer width;

    @JsonProperty("height")
    private Integer height;

    @JsonProperty("depth")
    private Integer depth;

}