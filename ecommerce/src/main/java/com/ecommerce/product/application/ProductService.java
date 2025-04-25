package com.ecommerce.product.application;

import com.ecommerce.product.application.dto.req.ProductCreateRequest;
import com.ecommerce.product.application.dto.req.ProductSearchRequest;
import com.ecommerce.product.application.dto.res.ProductCreatedResponse;
import com.ecommerce.product.application.dto.res.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductCreatedResponse create(ProductCreateRequest request);

    Page<ProductResponse> findProducts(ProductSearchRequest request);
}
