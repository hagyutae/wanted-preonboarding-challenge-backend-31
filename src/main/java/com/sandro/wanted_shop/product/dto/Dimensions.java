package com.sandro.wanted_shop.product.dto;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Dimensions {
    private float depth;
    private float width;
    private float height;
}