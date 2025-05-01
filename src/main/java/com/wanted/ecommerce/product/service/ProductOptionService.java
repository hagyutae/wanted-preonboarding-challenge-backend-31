package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import com.wanted.ecommerce.product.dto.request.ProductOptionRequest;
import java.util.List;

public interface ProductOptionService {

    List<ProductOption>  saveAllProductOption(List<ProductOptionRequest> optionRequests, ProductOptionGroup optionGroup);

    ProductOption findOptionById(Long optionId);

    Boolean isExistStock(Long productId, Integer compStock);
}
