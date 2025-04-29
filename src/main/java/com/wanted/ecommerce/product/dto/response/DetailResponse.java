package com.wanted.ecommerce.product.dto.response;

import java.util.Map;
import lombok.Builder;

@Builder
public record DetailResponse(
    Double weight,
    DimensionsResponse dimensions,
    String materials,
    String countryOfOrigin,
    String warrantyInfo,
    String careInstructions,
    Map<String, Object> additionalInfo
) {

    public static DetailResponse of(Double weight, DimensionsResponse dimensions,
        String materials, String countryOfOrigin, String warrantyInfo, String careInstructions,
        Map<String, Object> additionalInfo) {
        return DetailResponse.builder()
            .weight(weight)
            .dimensions(dimensions)
            .materials(materials)
            .countryOfOrigin(countryOfOrigin)
            .warrantyInfo(warrantyInfo)
            .careInstructions(careInstructions)
            .additionalInfo(additionalInfo)
            .build();
    }
}
