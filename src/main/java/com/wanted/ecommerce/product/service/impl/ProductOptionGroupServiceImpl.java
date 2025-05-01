package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import com.wanted.ecommerce.product.dto.request.ProductOptionGroupRequest;
import com.wanted.ecommerce.product.dto.response.ProductOptionGroupResponse;
import com.wanted.ecommerce.product.dto.response.ProductOptionResponse;
import com.wanted.ecommerce.product.repository.ProductOptionGroupRepository;
import com.wanted.ecommerce.product.service.ProductOptionGroupService;
import com.wanted.ecommerce.product.service.ProductOptionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductOptionGroupServiceImpl implements ProductOptionGroupService {
    private final ProductOptionGroupRepository optionGroupRepository;
    private final ProductOptionService productOptionService;

    @Override
    public ProductOptionGroup saveProductOptionGroup(Product product, ProductOptionGroupRequest groupRequest) {
        ProductOptionGroup optionGroup = ProductOptionGroup.of(product,
            groupRequest.getName(), groupRequest.getDisplayOrder());
        return optionGroupRepository.save(optionGroup);
    }

    @Override
    public List<Long> createProductOptions(Product saved,
        List<ProductOptionGroupRequest> optionGroups) {
        return optionGroups.stream()
            .map(groupRequest -> {
                ProductOptionGroup optionGroup = saveProductOptionGroup( saved, groupRequest);
                productOptionService.saveAllProductOption(groupRequest.getOptions(), optionGroup);
                return optionGroup.getId();
            })
            .toList();
    }

    @Override
    public List<ProductOptionGroupResponse> createOptionGroupResponse(
        List<ProductOptionGroup> optionGroups) {
        return optionGroups.stream()
            .map(optionGroup -> {
                List<ProductOptionResponse> options = optionGroup.getOptions().stream()
                    .map(option -> ProductOptionResponse.of(
                        option.getId(), option.getName(), option.getAdditionalPrice().doubleValue(),
                        option.getSku(), option.getStock(), option.getDisplayOrder()
                    ))
                    .toList();

                return ProductOptionGroupResponse.of(optionGroup.getId(), optionGroup.getName(),
                    optionGroup.getDisplayOrder(), options);
            })
            .toList();
    }
}
