package com.psh10066.commerce.domain.service;

import com.psh10066.commerce.api.dto.request.CreateProductOptionRequest;
import com.psh10066.commerce.api.dto.request.UpdateProductOptionRequest;
import com.psh10066.commerce.api.dto.response.CreateProductOptionResponse;
import com.psh10066.commerce.api.dto.response.UpdateProductOptionResponse;
import com.psh10066.commerce.domain.exception.ResourceNotFoundException;
import com.psh10066.commerce.domain.model.product.ProductOption;
import com.psh10066.commerce.domain.model.product.ProductOptionGroup;
import com.psh10066.commerce.domain.model.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductOptionService {

    private final ProductRepository productRepository;

    @Transactional
    public CreateProductOptionResponse createProductOption(Long id, CreateProductOptionRequest request) {
        ProductOptionGroup productOptionGroup = productRepository.getProductOptionGroupByOptionGroupId(request.optionGroupId());
        if (!productOptionGroup.getProduct().getId().equals(id)) {
            throw new ResourceNotFoundException("ProductOptionGroup", request.optionGroupId());
        }

        ProductOption productOption = new ProductOption(
            productOptionGroup,
            request.name(),
            request.additionalPrice(),
            request.sku(),
            request.stock(),
            request.displayOrder()
        );

        productRepository.saveProductOption(productOption);

        return new CreateProductOptionResponse(
            productOption.getId(),
            productOptionGroup.getId(),
            productOption.getName(),
            productOption.getAdditionalPrice(),
            productOption.getSku(),
            productOption.getStock(),
            productOption.getDisplayOrder()
        );
    }

    @Transactional
    public UpdateProductOptionResponse updateProductOption(Long id, Long optionId, UpdateProductOptionRequest request) {

        ProductOption productOption = productRepository.getProductOptionByOptionId(optionId);
        ProductOptionGroup productOptionGroup = productOption.getOptionGroup();
        if (!productOptionGroup.getId().equals(request.optionGroupId())) {
            productOptionGroup = productRepository.getProductOptionGroupByOptionGroupId(request.optionGroupId());
            if (!productOptionGroup.getProduct().getId().equals(id)) {
                throw new ResourceNotFoundException("ProductOptionGroup", request.optionGroupId());
            }
        }

        productOption.update(
            productOptionGroup,
            request.name(),
            request.additionalPrice(),
            request.sku(),
            request.stock(),
            request.displayOrder()
        );

        productRepository.saveProductOption(productOption);

        return new UpdateProductOptionResponse(
            productOption.getId(),
            productOptionGroup.getId(),
            productOption.getName(),
            productOption.getAdditionalPrice(),
            productOption.getSku(),
            productOption.getStock(),
            productOption.getDisplayOrder()
        );
    }
}
