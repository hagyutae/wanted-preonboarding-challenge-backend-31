package com.wanted.ecommerce.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DimensionsRequest {

    private Integer width;
    private Integer height;
    private Integer depth;

    public static DimensionsRequest of(int width, int height, int depth){
        return DimensionsRequest.builder()
            .width(width)
            .height(height)
            .depth(depth)
            .build();
    }
}
