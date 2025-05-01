package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import com.wanted.ecommerce.product.dto.request.ProductOptionGroupRequest;
import com.wanted.ecommerce.product.dto.response.ProductOptionGroupResponse;
import java.util.List;

public interface ProductOptionGroupService {
    ProductOptionGroup saveProductOptionGroup(Product product, ProductOptionGroupRequest groupRequest);
    List<Long> createProductOptions(Product saved,List<ProductOptionGroupRequest> optionGroups);

    List<ProductOptionGroupResponse> createOptionGroupResponse(
        List<ProductOptionGroup> optionGroups);
}
