package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import com.wanted.ecommerce.product.dto.request.ProductOptionRequest;
import com.wanted.ecommerce.product.dto.response.ProductOptionResponse;
import java.util.List;

public interface ProductOptionService {

    List<ProductOption> saveAllProductOption(List<ProductOptionRequest> optionRequests, ProductOptionGroup optionGroup);

    ProductOption findOptionById(Long optionId);

    ProductOptionResponse createProductOption(Product product, ProductOptionGroup optionGroup, ProductOptionRequest optionRequest);

    Boolean isExistStock(Long productId, Integer compStock);
}
