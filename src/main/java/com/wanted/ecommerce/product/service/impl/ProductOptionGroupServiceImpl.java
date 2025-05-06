package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import com.wanted.ecommerce.product.dto.request.ProductRegisterRequest.ProductOptionGroupRequest;
import com.wanted.ecommerce.product.dto.response.ProductOptionResponse;
import com.wanted.ecommerce.product.dto.response.ProductResponse.ProductOptionGroupResponse;
import com.wanted.ecommerce.product.repository.ProductOptionGroupRepository;
import com.wanted.ecommerce.product.repository.ProductOptionRepository;
import com.wanted.ecommerce.product.service.ProductOptionGroupService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionGroupServiceImpl implements ProductOptionGroupService {

    private final ProductOptionGroupRepository optionGroupRepository;
    private final ProductOptionRepository optionRepository;

    @Transactional
    @Override
    public ProductOptionGroup saveOptionGroup(Product product,
        ProductOptionGroupRequest groupRequest) {
        ProductOptionGroup optionGroup = ProductOptionGroup.of(product, groupRequest);
        return optionGroupRepository.save(optionGroup);
    }

    @Transactional
    @Override
    public List<ProductOptionGroup> saveProductOptionsAndGroup(Product saved,
        List<ProductOptionGroupRequest> optionGroups) {
        return optionGroups.stream()
            .map(groupRequest -> {
                ProductOptionGroup savedOptionGroup = saveOptionGroup(saved, groupRequest);

                List<ProductOption> options = groupRequest.getOptions().stream()
                    .map(optionRequest -> ProductOption.of(
                        savedOptionGroup, optionRequest
                    ))
                    .toList();
                optionRepository.saveAll(options);
                return savedOptionGroup;
            })
            .toList();
    }

    @Transactional
    @Override
    public void deleteProductOptionGroup(Long productId) {
        optionGroupRepository.deleteByProductId(productId);
    }

    @Override
    public List<ProductOptionGroupResponse> createOptionGroupResponse(
        List<ProductOptionGroup> optionGroups) {
        return optionGroups.stream()
            .map(optionGroup -> {
                List<ProductOptionResponse> options = optionGroup.getOptions().stream()
                    .map(ProductOptionResponse::of)
                    .toList();

                return ProductOptionGroupResponse.of(optionGroup, options);
            })
            .toList();
    }

    private ProductOptionGroup getOptionGroupById(Long id) {
        return optionGroupRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));
    }

    @Override
    public ProductOptionGroup updateOptionGroup(Product product, Long optionGroupId) {
        ProductOptionGroup optionGroup = getOptionGroupById(optionGroupId);
        optionGroup.updateProduct(product);
        return optionGroup;
    }
}
