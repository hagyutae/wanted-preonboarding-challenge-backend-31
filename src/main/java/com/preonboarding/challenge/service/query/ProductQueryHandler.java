package com.preonboarding.challenge.service.query;

import com.preonboarding.challenge.controller.dto.ProductListResponse;
import com.preonboarding.challenge.service.product.ProductDto;

public interface ProductQueryHandler {
    // 상품 조회
    ProductDto.Product getProduct(ProductQuery.GetProduct query);

    ProductListResponse getProducts(ProductQuery.ListProducts query);
}
