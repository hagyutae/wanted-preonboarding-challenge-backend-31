package com.ecommerce.products.application.service;

import com.ecommerce.products.application.dto.ProductDto;
import com.ecommerce.products.controller.dto.ProductListResponse;

public interface ProductService {

    // 상품 관리
    ProductDto.Product createProduct(ProductDto.CreateRequest request);

    ProductDto.Product updateProduct(Long productId, ProductDto.UpdateRequest request);

    void deleteProduct(Long productId);

    // 옵션 관리
    ProductDto.Option addProductOption(Long productId, ProductDto.Option option);

    ProductDto.Option updateProductOption(Long productId, ProductDto.Option option);

    void deleteProductOption(Long productId, Long optionId);

    // 이미지 관리
    ProductDto.Image addProductImage(Long productId, ProductDto.Image image);

    // 상품 조회
    ProductDto.Product getProductById(Long productId);

    ProductListResponse getProducts(ProductDto.ListRequest request);
}