package com.wanted.ecommerce.product.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dimensions {
    private Integer width;
    private Integer height;
    private Integer depth;

    public static Dimensions of(Integer width, Integer height, Integer depth){
        return Dimensions.builder()
            .width(width)
            .height(height)
            .depth(depth)
            .build();
    }
}
