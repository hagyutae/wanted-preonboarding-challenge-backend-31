package com.wanted.mono.domain.product.service;

import com.wanted.mono.domain.product.dto.request.ProductOptionRequest;
import com.wanted.mono.domain.product.entity.ProductOption;
import com.wanted.mono.domain.product.entity.ProductOptionGroup;
import com.wanted.mono.domain.product.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductOptionService {
    private final ProductOptionRepository productOptionRepository;

    @Transactional
    public void createProductOptions(List<ProductOptionRequest> productOptionRequests, ProductOptionGroup productOptionGroup) {
        List<ProductOption> productOptions = new ArrayList<>();
        log.info("ProductOption 엔티티화");
        for (ProductOptionRequest productOptionRequest : productOptionRequests) {
            ProductOption productOption = ProductOption.of(productOptionRequest);
            productOption.addOptionGroup(productOptionGroup);
            productOptions.add(productOption);
        }
        log.info("ProductOption 리스트 저장");
        productOptionRepository.saveAll(productOptions);
    }

    @Transactional
    public Long createProductOption(ProductOptionRequest productOptionRequest, ProductOptionGroup productOptionGroup) {
        ProductOption productOption = ProductOption.of(productOptionRequest);
        productOption.addOptionGroup(productOptionGroup);
        productOptionRepository.save(productOption);
        return productOption.getId();
    }
}
