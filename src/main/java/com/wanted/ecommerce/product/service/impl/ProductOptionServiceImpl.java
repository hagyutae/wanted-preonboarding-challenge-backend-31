package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import com.wanted.ecommerce.product.dto.request.ProductOptionRequest;
import com.wanted.ecommerce.product.dto.response.ProductOptionResponse;
import com.wanted.ecommerce.product.repository.ProductOptionRepository;
import com.wanted.ecommerce.product.service.ProductOptionGroupService;
import com.wanted.ecommerce.product.service.ProductOptionService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {
    private final ProductOptionGroupService optionGroupService;
    private final ProductOptionRepository optionRepository;

    @Transactional
    @Override
    public ProductOptionResponse addProductOption(Product product, ProductOptionRequest optionRequest) {
        ProductOptionGroup optionGroup = optionGroupService.updateOptionGroup(product,
            optionRequest.getOptionGroupId());

        ProductOption option = ProductOption.of(optionGroup, optionRequest);
        ProductOption savedOption = optionRepository.save(option);
        return ProductOptionResponse.of(savedOption);
    }

    @Transactional
    @Override
    public ProductOptionResponse updateProductOption(long productId, long optionId,
        ProductOptionRequest optionRequest) {
        ProductOption option = optionRepository.findById(optionId)
            .orElseThrow(() -> new ResourceNotFoundException(
                ErrorType.RESOURCE_NOT_FOUND));

        checkSameProductId(option.getOptionGroup().getProduct(), productId);

        option.update(optionRequest);

        return ProductOptionResponse.of(option);
    }

    @Transactional
    @Override
    public void deleteProductOption(long productId, long optionId) {
        ProductOption option = optionRepository.findById(optionId)
            .orElseThrow(() -> new ResourceNotFoundException(
                ErrorType.RESOURCE_NOT_FOUND));

        checkSameProductId(option.getOptionGroup().getProduct(), productId);

        optionRepository.delete(option);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean isExistStock(Long productId, Integer compStock) {
        return optionRepository.existsByOptionGroupProductIdAndStockGreaterThan(
            productId, compStock);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductOption getOptionById(Long optionId) {
        return Optional.ofNullable(optionId)
            .flatMap(optionRepository::findById)
            .orElse(null);
    }

    private void checkSameProductId(Product product, long productId){
        if(product.getId() != productId){
            throw new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND);
        }
    }
}
