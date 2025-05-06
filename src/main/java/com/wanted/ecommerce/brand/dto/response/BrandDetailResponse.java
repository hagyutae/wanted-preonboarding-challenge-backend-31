package com.wanted.ecommerce.brand.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanted.ecommerce.brand.domain.Brand;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BrandDetailResponse(
    Long id,
    String name,
    String description,
    String logoUrl,
    String website
){
    public static BrandDetailResponse of(Brand brand){
        return BrandDetailResponse.builder()
            .id(brand.getId())
            .name(brand.getName())
            .description(brand.getDescription())
            .logoUrl(brand.getLogoUrl())
            .website(brand.getWebsite())
            .build();
    }
}
