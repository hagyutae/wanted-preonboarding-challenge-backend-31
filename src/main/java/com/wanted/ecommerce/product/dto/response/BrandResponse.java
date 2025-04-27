package com.wanted.ecommerce.product.dto.response;

import lombok.Builder;

@Builder
public record BrandResponse (
    Long id,
    String name
){
    public static BrandResponse of(Long id, String name){
        return BrandResponse.builder()
            .id(id)
            .name(name)
            .build();
    }
}
