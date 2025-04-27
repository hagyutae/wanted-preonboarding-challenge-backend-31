package com.wanted.ecommerce.product.dto.response;

import lombok.Builder;

@Builder
public record SellerResponse(
    Long id,
    String name
) {
    public static SellerResponse of(Long id, String name){
        return SellerResponse.builder()
            .id(id)
            .name(name)
            .build();
    }
}
