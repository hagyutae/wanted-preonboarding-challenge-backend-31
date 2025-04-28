package com.wanted.ecommerce.product.dto.response;

import lombok.Builder;

@Builder
public record DimensionsResponse(
    Integer width,
    Integer height,
    Integer depth
) {

    public static DimensionsResponse of(Integer width, Integer height, Integer depth) {
        return DimensionsResponse.builder()
            .width(width)
            .height(height)
            .depth(depth)
            .build();
    }
}
