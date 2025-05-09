package com.wanted.mono.domain.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted.mono.domain.product.dto.Dimension;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailRequest {
    private BigDecimal weight;
    private Dimension dimensions;
    private String materials;
    @JsonProperty("country_of_origin")
    private String countryOfOrigin;
    @JsonProperty("warranty_info")
    private String warrantyInfo;
    @JsonProperty("care_instructions")
    private String careInstructions;
    @JsonProperty("additional_info")
    private Map<String, Object> additionalInfo;

}