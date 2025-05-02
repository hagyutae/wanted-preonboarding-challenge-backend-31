package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import com.wanted.ecommerce.product.dto.request.ProductOptionGroupRequest;
import com.wanted.ecommerce.product.dto.response.ProductOptionGroupResponse;
import java.util.List;

public interface ProductOptionGroupService {
    ProductOptionGroup saveOptionGroup(Product product, ProductOptionGroupRequest groupRequest);
    List<ProductOptionGroup> saveProductOptionsAndGroup(Product saved,List<ProductOptionGroupRequest> optionGroups);

    void deleteProductOptionGroup(Long productId);

    List<ProductOptionGroupResponse> createOptionGroupResponse(
        List<ProductOptionGroup> optionGroups);
}
