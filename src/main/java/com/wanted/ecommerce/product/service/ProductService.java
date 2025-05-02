package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.dto.request.ProductCreateRequest;
import com.wanted.ecommerce.product.dto.request.ProductImageRequest;
import com.wanted.ecommerce.product.dto.request.ProductOptionRequest;
import com.wanted.ecommerce.product.dto.request.ProductReadAllRequest;
import com.wanted.ecommerce.product.dto.response.ProductDetailResponse;
import com.wanted.ecommerce.product.dto.response.ProductImageCreateResponse;
import com.wanted.ecommerce.product.dto.response.ProductListResponse;
import com.wanted.ecommerce.product.dto.response.ProductOptionResponse;
import com.wanted.ecommerce.product.dto.response.ProductResponse;
import com.wanted.ecommerce.product.dto.response.ProductUpdateResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductResponse create(ProductCreateRequest request);

    Page<ProductListResponse> readAll(ProductReadAllRequest request);

    ProductDetailResponse readDetail(long productId);

    ProductUpdateResponse update(long productId, ProductCreateRequest request);

    void delete(long productId);

    ProductOptionResponse addProductOption(long id, ProductOptionRequest optionRequest);

    ProductOptionResponse updateProductOption(long id, long optionId, ProductOptionRequest optionRequest);

    void deleteProductOption(long id, long optionId);

    ProductImageCreateResponse addProductImage(long id, ProductImageRequest imageRequest);
}
