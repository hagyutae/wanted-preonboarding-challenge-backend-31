package com.wanted.ecommerce.brand.dto.response;

import com.wanted.ecommerce.brand.domain.Brand;
import lombok.Builder;

@Builder
public record BrandResponse (
    Long id,
    String name
){
    public static BrandResponse of(Brand brand){
        return BrandResponse.builder()
            .id(brand.getId())
            .name(brand.getName())
            .build();
    }
}
