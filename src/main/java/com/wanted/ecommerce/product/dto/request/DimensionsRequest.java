package com.wanted.ecommerce.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DimensionsRequest {

    private Integer width;
    private Integer height;
    private Integer depth;
}
