package com.wanted.ecommerce.product.dto.response;

import lombok.Builder;

@Builder
public record AdditionalInfoResponse(
    Boolean assemblyRequired,
    String assemblyTime
) {
    public static AdditionalInfoResponse of(Boolean assemblyRequired, String assemblyTime){
        return AdditionalInfoResponse.builder()
            .assemblyRequired(assemblyRequired)
            .assemblyTime(assemblyTime)
            .build();
    }
}
