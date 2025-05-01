package com.example.preonboarding.options.service;

import com.example.preonboarding.options.domain.ProductOption;
import com.example.preonboarding.options.domain.ProductOptionGroup;
import com.example.preonboarding.options.repository.ProductOptionGroupRepository;
import com.example.preonboarding.options.repository.ProductOptionRepository;
import com.example.preonboarding.request.ProductOptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionService {
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionRepository productOptionRepository;

    @Transactional
    public ProductOption addProductOptions(Long id, ProductOptionRequest request) {
        ProductOptionGroup productOptionGroup = productOptionGroupRepository.findById(request.getOptionGroupId()).orElseThrow(() -> new IllegalArgumentException("not found optionGroup"));

        ProductOption newOption = ProductOption.builder()
                .name(request.getName())
                .additionalPrice(request.getAdditionalPrice())
                .sku(request.getSku())
                .displayOrder(request.getDisplayOrder())
                .stock(request.getStock())
                .build();

        productOptionGroup.getOptionGroups().add(newOption);

        ProductOption savedOption = productOptionRepository.save(newOption);

        return savedOption;
    }

    @Transactional
    public ProductOption updateProductOptions(Long id, Long optionId, ProductOptionRequest request) {
        ProductOption productOption = productOptionRepository.findById(optionId).orElseThrow(() -> new IllegalArgumentException("not found option"));

        productOption.setName(request.getName());
        productOption.setSku(request.getSku());
        productOption.setAdditionalPrice(request.getAdditionalPrice());
        productOption.setStock(request.getStock());
        productOption.setDisplayOrder(request.getDisplayOrder());

        if (request.getOptionGroupId() != null) {
            ProductOptionGroup oldGroup = productOption.getOptionGroups();
            ProductOptionGroup newGroup = productOptionGroupRepository.findById(request.getOptionGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("not found option group"));

            if(oldGroup == null || !oldGroup.equals(newGroup)){
                if(oldGroup != null){
                    oldGroup.getOptionGroups().remove(productOption);
                }
                newGroup.addOption(productOption);
            }
        }

        return productOption;
    }

    @Transactional
    public void deleteProductOptions(Long id, Long optionId, ProductOptionRequest request) {
        ProductOption productOption = productOptionRepository.findById(optionId).orElseThrow(() -> new IllegalArgumentException("not found option"));

        ProductOptionGroup productOptionGroup = productOption.getOptionGroups();
        if(productOptionGroup != null){
            productOptionGroup.getOptionGroups().remove(productOption);
            productOption.setOptionGroups(null);
        }

        productOptionRepository.delete(productOption);
    }
}
