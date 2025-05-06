package com.challenge.onboarding.product.service;


import com.challenge.onboarding.product.service.dto.request.CreateProductRequest;
import com.challenge.onboarding.product.service.dto.response.CreateProductResponse;

public interface ProductService {
    CreateProductResponse createProduct(CreateProductRequest createProductRequest);
}
