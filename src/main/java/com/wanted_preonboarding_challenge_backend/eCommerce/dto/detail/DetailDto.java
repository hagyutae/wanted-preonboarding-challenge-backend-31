package com.wanted_preonboarding_challenge_backend.eCommerce.dto.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailDto {
    private Double weight;

    @JsonProperty("dimensions")
    private DimensionsDto dimensions;

    @JsonProperty("materials")
    private String materials;

    @JsonProperty("country_of_origin")
    private String countryOfOrigin;

    @JsonProperty("warranty_info")
    private String warrantyInfo;

    @JsonProperty("care_instructions")
    private String careInstructions;

    @JsonProperty("additional_info")
    private AdditionalInfoDto additionalInfo;
}
