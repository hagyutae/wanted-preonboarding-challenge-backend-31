package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.dto.request.ProductCreateRequest;
import com.wanted.ecommerce.product.dto.response.ProductResponse;

public interface ProductService {

    public ProductResponse create(ProductCreateRequest request);
}
