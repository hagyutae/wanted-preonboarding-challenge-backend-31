package com.ecommerce.product.domain;

import com.ecommerce.product.application.dto.req.ProductCreateRequest;

public record DimensionsVO(
        int width,
        int height,
        int depth
) {
    public static DimensionsVO toDimensionsVO(ProductCreateRequest.ProductDetailRequest.DimensionsRequest dimensionsRequest) {
        return new DimensionsVO(dimensionsRequest.width(), dimensionsRequest.height(), dimensionsRequest.depth());
    }
}
