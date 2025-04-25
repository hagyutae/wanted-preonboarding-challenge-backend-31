package com.example.wanted_preonboarding_challenge_backend_31.domain.model.product;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ProductDimensions implements Serializable {

    private int width;
    private int height;
    private int depth;


    public static ProductDimensions create(int width, int height, int depth) {
        return ProductDimensions.builder()
                .width(width)
                .height(height)
                .depth(depth)
                .build();
    }
}
