package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.dto.request.ProductCreateRequest;
import com.wanted.ecommerce.product.dto.request.ProductReadAllRequest;
import com.wanted.ecommerce.product.dto.response.ProductDetailResponse;
import com.wanted.ecommerce.product.dto.response.ProductListResponse;
import com.wanted.ecommerce.product.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductResponse create(ProductCreateRequest request);

    Page<ProductListResponse> readAll(ProductReadAllRequest request);

    ProductDetailResponse readDetail(long productId);
}
