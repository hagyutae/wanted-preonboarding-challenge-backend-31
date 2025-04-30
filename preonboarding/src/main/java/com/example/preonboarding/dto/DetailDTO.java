package com.example.preonboarding.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DetailDTO {


    private Double weight;
    private Dimensions dimensions;
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    private AdditionalInfo additionalInfo;

}


