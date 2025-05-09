package com.wanted.mono.domain.product.service;

import com.wanted.mono.domain.product.dto.request.ProductPriceRequest;
import com.wanted.mono.domain.product.entity.Product;
import com.wanted.mono.domain.product.entity.ProductPrice;
import com.wanted.mono.domain.product.repository.ProductPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductPriceService {
    private final ProductPriceRepository productPriceRepository;

    @Transactional
    public Long createProductPrice(ProductPriceRequest productPriceRequest, Product product) {
        log.info("ProductPriceRequest : {}", productPriceRequest);

        log.info("ProductPrice 생성");
        ProductPrice productPrice = ProductPrice.of(productPriceRequest);

        log.info("ProductPrice, Product 연관관계 설정");
        productPrice.addProduct(product);

        log.info("ProductPrice 저장");
        return productPriceRepository.save(productPrice).getId();
    }

    @Transactional
    public void updateProductPrice(ProductPriceRequest productPriceRequest, Product product) {
        ProductPrice productPrice = productPriceRepository.findAllByProduct(product);

        productPrice.update(productPriceRequest);
    }
}
