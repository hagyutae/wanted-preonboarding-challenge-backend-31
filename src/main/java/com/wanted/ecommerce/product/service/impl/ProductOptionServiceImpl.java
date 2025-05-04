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
import com.wanted.ecommerce.product.service.ProductService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {
    private final ProductService productService;
    private final ProductOptionGroupService optionGroupService;
    private final ProductOptionRepository optionRepository;


    @Transactional
    @Override
    public ProductOptionResponse addProductOption(long productId, ProductOptionRequest optionRequest) {
       Product product = productService.getProductById(productId);

        ProductOptionGroup optionGroup = optionGroupService.updateOptionGroup(product,
            optionRequest.getOptionGroupId());

        ProductOption option = ProductOption.of(optionGroup, optionRequest.getName(),
            optionRequest.getAdditionalPrice(), optionRequest.getSku(), optionRequest.getStock(),
            optionRequest.getDisplayOrder());

        ProductOption saved = optionRepository.save(option);

        return ProductOptionResponse.of(saved.getId(), saved.getOptionGroup().getId(),
            saved.getName(), saved.getAdditionalPrice().doubleValue(), saved.getSku(),
            saved.getStock(), saved.getDisplayOrder());
    }

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

    @Transactional
    @Override
    public ProductOptionResponse updateProductOption(long productId, long optionId,
        ProductOptionRequest optionRequest) {
        ProductOption option = optionRepository.findById(optionId)
            .orElseThrow(() -> new ResourceNotFoundException(
                ErrorType.RESOURCE_NOT_FOUND));

        checkSameProductId(option.getOptionGroup().getProduct(), productId);

        option.update(optionRequest.getName(), optionRequest.getAdditionalPrice(),
            optionRequest.getSku(), optionRequest.getStock(), optionRequest.getDisplayOrder());

        return ProductOptionResponse.of(option.getId(), option.getOptionGroup().getId(),
            option.getName(), option.getAdditionalPrice().doubleValue(), option.getSku(),
            option.getStock(), option.getDisplayOrder());
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
