package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductDetail;
import com.wanted.ecommerce.product.dto.request.ProductDetailRequest;
import com.wanted.ecommerce.product.dto.response.ProductDetailResponse.DetailResponse;

public interface ProductDetailService {

    ProductDetail saveDetail(Product product, ProductDetailRequest detailRequest);

    void updateDetail(ProductDetail detail, ProductDetailRequest request);

    DetailResponse createProductDetailResponse(ProductDetail detail);

    ProductDetail getProductDetailByProductId(Long productId);
}
