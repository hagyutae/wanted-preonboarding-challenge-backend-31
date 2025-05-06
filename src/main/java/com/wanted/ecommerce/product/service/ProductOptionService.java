package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.dto.request.ProductOptionRequest;
import com.wanted.ecommerce.product.dto.response.ProductOptionResponse;

public interface ProductOptionService {

    ProductOptionResponse addProductOption(Product product, ProductOptionRequest optionRequest);

    ProductOption getOptionById(Long optionId);

    ProductOptionResponse updateProductOption(long productId, long optionId,ProductOptionRequest optionRequest);

    void deleteProductOption(long productId, long optionId);

    Boolean isExistStock(Long productId, Integer compStock);
}
