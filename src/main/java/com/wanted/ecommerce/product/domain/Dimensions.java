package com.wanted.ecommerce.product.domain;

import com.wanted.ecommerce.product.dto.request.ProductRegisterRequest.DimensionsRequest;
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

    public static Dimensions of(DimensionsRequest request){
        return Dimensions.builder()
            .width(request.getWidth())
            .height(request.getHeight())
            .depth(request.getDepth())
            .build();
    }
}
