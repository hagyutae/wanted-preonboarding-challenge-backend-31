package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import com.wanted.ecommerce.product.dto.request.ProductOptionRequest;
import com.wanted.ecommerce.product.dto.response.ProductOptionResponse;
import java.util.List;

public interface ProductOptionService {

    ProductOptionResponse addProductOption(long productId, ProductOptionRequest optionRequest);

    List<ProductOption> saveAllProductOption(List<ProductOptionRequest> optionRequests, ProductOptionGroup optionGroup);

    ProductOption getOptionById(Long optionId);

    ProductOptionResponse updateProductOption(long productId, long optionId,ProductOptionRequest optionRequest);

    void deleteProductOption(long productId, long optionId);

    Boolean isExistStock(Long productId, Integer compStock);
}
