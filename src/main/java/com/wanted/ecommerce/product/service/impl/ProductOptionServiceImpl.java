package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import com.wanted.ecommerce.product.dto.request.ProductOptionRequest;
import com.wanted.ecommerce.product.dto.response.ProductOptionResponse;
import com.wanted.ecommerce.product.repository.ProductOptionRepository;
import com.wanted.ecommerce.product.service.ProductOptionService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {

    private final ProductOptionRepository optionRepository;


    @Transactional
    @Override
    public List<ProductOption> saveAllProductOption(List<ProductOptionRequest> optionRequests,
        ProductOptionGroup optionGroup) {
        List<ProductOption> options = optionRequests.stream()
            .map(optionRequest -> ProductOption.of(
                optionGroup,
                optionRequest.getName(),
                optionRequest.getAdditionalPrice(),
                optionRequest.getSku(),
                optionRequest.getStock(),
                optionRequest.getDisplayOrder()
            ))
            .toList();
        return optionRepository.saveAll(options);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductOption findOptionById(Long optionId) {
        return Optional.ofNullable(optionId)
            .flatMap(optionRepository::findById)
            .orElse(null);
    }

    @Transactional
    @Override
    public ProductOptionResponse createProductOption(Product product,
        ProductOptionGroup optionGroup,
        ProductOptionRequest optionRequest) {

        ProductOption option = ProductOption.of(optionGroup, optionRequest.getName(),
            optionRequest.getAdditionalPrice(), optionRequest.getSku(), optionRequest.getStock(),
            optionRequest.getDisplayOrder());

        ProductOption saved = optionRepository.save(option);
        return ProductOptionResponse.of(saved.getId(), saved.getOptionGroup().getId(),
            saved.getName(), saved.getAdditionalPrice().doubleValue(), saved.getSku(),
            saved.getStock(), saved.getDisplayOrder());
    }

    @Override
    public ProductOptionResponse updateProductOption(long optionId, Product product,
        ProductOptionRequest optionRequest) {
        ProductOption option = optionRepository.findById(optionId)
            .orElseThrow(() -> new ResourceNotFoundException(
                ErrorType.RESOURCE_NOT_FOUND));

        option.update(optionRequest.getName(), optionRequest.getAdditionalPrice(),
            optionRequest.getSku(), optionRequest.getStock(), optionRequest.getDisplayOrder());

        return ProductOptionResponse.of(option.getId(), option.getOptionGroup().getId(),
            option.getName(), option.getAdditionalPrice().doubleValue(), option.getSku(),
            option.getStock(), option.getDisplayOrder());
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean isExistStock(Long productId, Integer compStock) {
        return optionRepository.existsByOptionGroupProductIdAndStockGreaterThan(
            productId, compStock);
    }
}
