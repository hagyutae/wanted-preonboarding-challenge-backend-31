package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.dto.request.ProductRegisterRequest;
import com.wanted.ecommerce.product.dto.request.ProductSearchRequest;
import com.wanted.ecommerce.product.dto.response.ProductListResponse;
import com.wanted.ecommerce.product.dto.response.ProductRegisterResponse;
import com.wanted.ecommerce.product.dto.response.ProductResponse;
import com.wanted.ecommerce.product.dto.response.ProductUpdateResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductRegisterResponse registerProduct(ProductRegisterRequest request);

    Page<ProductListResponse> readAll(ProductSearchRequest request);

    ProductResponse readDetail(long productId);

    ProductUpdateResponse updateProduct(long productId, ProductRegisterRequest request);

    void deleteProduct(long productId);

    Product getProductById(long id);
}
