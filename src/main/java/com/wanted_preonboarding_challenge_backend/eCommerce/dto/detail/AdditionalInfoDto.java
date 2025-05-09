package com.wanted_preonboarding_challenge_backend.eCommerce.dto.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdditionalInfoDto {
    @JsonProperty("assembly_required")
    private Boolean assemblyRequired;

    @JsonProperty("assembly_time")
    private String assemblyTime;
}
