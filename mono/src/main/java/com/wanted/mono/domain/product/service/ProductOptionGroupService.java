package com.wanted.mono.domain.product.service;

import com.wanted.mono.domain.product.dto.ProductOptionGroupRequest;
import com.wanted.mono.domain.product.entity.Product;
import com.wanted.mono.domain.product.entity.ProductOptionGroup;
import com.wanted.mono.domain.product.repository.ProductOptionGroupRepository;
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
public class ProductOptionGroupService {
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionService productOptionService;

    @Transactional
    public void createProductOptionGroup(List<ProductOptionGroupRequest> productOptionGroupRequests, Product product) {
        List<ProductOptionGroup> productOptionGroupEntities = new ArrayList<>();
        for (ProductOptionGroupRequest productOptionGroupRequest : productOptionGroupRequests) {
            log.info("ProductOptionGroup 엔티티 생성");
            ProductOptionGroup productOptionGroup = ProductOptionGroup.of(productOptionGroupRequest);

            log.info("ProductOptionGroup, Product 연관관계 주입");
            productOptionGroup.addProduct(product);

            productOptionGroupEntities.add(productOptionGroup);

            log.info("ProductOptionGroup -> List<ProductOption> 엔티티 화");
            productOptionService.createProductOption(productOptionGroupRequest.getOptions(), productOptionGroup);
        }

        log.info("ProductOptionGroup 엔티티 리스트 저장");
        productOptionGroupRepository.saveAll(productOptionGroupEntities);
    }
}
