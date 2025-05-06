package com.shopping.mall.product.service;

import com.shopping.mall.common.exception.ConflictException;
import com.shopping.mall.common.exception.ResourceNotFoundException;
import com.shopping.mall.product.dto.request.ProductOptionCreateRequest;
import com.shopping.mall.product.dto.request.ProductOptionUpdateRequest;
import com.shopping.mall.product.entity.Product;
import com.shopping.mall.product.entity.ProductOption;
import com.shopping.mall.product.entity.ProductOptionGroup;
import com.shopping.mall.product.repository.ProductOptionGroupRepository;
import com.shopping.mall.product.repository.ProductOptionRepository;
import com.shopping.mall.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionService {

    private final ProductRepository productRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionRepository productOptionRepository;

    @Transactional
    public void addOptionGroup(Long productId, ProductOptionCreateRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("상품이 존재하지 않습니다"));

        ProductOptionGroup optionGroup = ProductOptionGroup.builder()
                .product(product)
                .name(request.getName())
                .displayOrder(request.getDisplayOrder())
                .build();

        productOptionGroupRepository.save(optionGroup);

        for (ProductOptionCreateRequest.OptionRequest optionReq : request.getOptions()) {
            ProductOption option = ProductOption.builder()
                    .optionGroup(optionGroup)
                    .name(optionReq.getName())
                    .additionalPrice(optionReq.getAdditionalPrice())
                    .sku(optionReq.getSku())
                    .stock(optionReq.getStock())
                    .displayOrder(optionReq.getDisplayOrder())
                    .build();

            productOptionRepository.save(option);
        }
    }

    @Transactional
    public void updateOption(Long optionId, ProductOptionUpdateRequest request) {

        ProductOption option = productOptionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("옵션이 존재하지 않습니다."));

        option.update(
                request.getName(),
                request.getAdditionalPrice(),
                request.getSku(),
                request.getStock(),
                request.getDisplayOrder()
        );
    }

    @Transactional
    public void deleteOption(Long productId, Long optionId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("상품을 찾을 수 없습니다."));

        ProductOption option = productOptionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("옵션을 찾을 수 없습니다."));

        if (!option.getOptionGroup().getProduct().getId().equals(productId)) {
            throw new ConflictException("해당 상품의 옵션이 아닙니다.");
        }

        productOptionRepository.delete(option);
    }
}
