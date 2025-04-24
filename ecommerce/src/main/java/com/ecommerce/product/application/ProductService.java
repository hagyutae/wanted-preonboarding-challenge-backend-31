package com.ecommerce.product.application;

import com.ecommerce.product.application.dto.req.ProductCreateRequest;
import com.ecommerce.product.application.dto.res.ProductCreatedResponse;

public interface ProductService {
    ProductCreatedResponse create(ProductCreateRequest request);
}
