package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductDetail;
import com.wanted.ecommerce.product.dto.request.ProductDetailRequest;
import com.wanted.ecommerce.product.dto.response.DetailResponse;

public interface ProductDetailService {

    Long saveDetail(Product product, ProductDetailRequest detailRequest);

    DetailResponse createProductDetailResponse(ProductDetail detail);
}
