package com.challenge.onboarding.product.service;


import com.challenge.onboarding.product.service.dto.request.CreateProductRequest;
import com.challenge.onboarding.product.service.dto.request.ProductSearchCondition;
import com.challenge.onboarding.product.service.dto.response.CreateProductResponse;
import com.challenge.onboarding.product.service.dto.response.ProductListResponse;

public interface ProductService {
    ProductListResponse getProductList(ProductSearchCondition request);
    CreateProductResponse createProduct(CreateProductRequest createProductRequest);
}
