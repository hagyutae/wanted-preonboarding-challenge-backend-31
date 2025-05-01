package com.example.preonboarding.price.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PriceDTO {

    @Min(value = 1, message = "기본 가격은 0보다 커야 합니다.")
    private int basePrice;
    private int salePrice;
    private int costPrice;
    private String currency;
    private double tax_rate;

}