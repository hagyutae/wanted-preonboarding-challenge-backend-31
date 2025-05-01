package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import com.wanted.ecommerce.product.dto.request.ProductOptionRequest;
import com.wanted.ecommerce.product.repository.ProductOptionRepository;
import com.wanted.ecommerce.product.service.ProductOptionService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {
    private final ProductOptionRepository optionRepository;


    @Override
    public List<ProductOption> saveAllProductOption(List<ProductOptionRequest> optionRequests, ProductOptionGroup optionGroup) {
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

    @Override
    public ProductOption findOptionById(Long optionId) {
        return Optional.ofNullable(optionId)
            .flatMap(optionRepository::findById)
            .orElse(null);
    }

    @Override
    public Boolean isExistStock(Long productId, Integer compStock) {
        return  optionRepository.existsByOptionGroupProductIdAndStockGreaterThan(
            productId, compStock);
    }
}
