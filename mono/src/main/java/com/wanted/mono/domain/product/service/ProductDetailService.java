package com.wanted.mono.domain.product.service;

import com.wanted.mono.domain.product.dto.request.ProductDetailRequest;
import com.wanted.mono.domain.product.entity.Product;
import com.wanted.mono.domain.product.entity.ProductDetail;
import com.wanted.mono.domain.product.repository.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductDetailService {
    private final ProductDetailRepository productDetailRepository;

    @Transactional
    public Long createProductDetail(ProductDetailRequest productDetailRequest, Product product) {
        log.info("ProductDetail 생성");
        ProductDetail productDetail = ProductDetail.of(productDetailRequest);

        log.info("ProductDetail, Product 연관관계 설정");
        productDetail.addProduct(product);

        log.info("ProductDetail 저장");
        productDetailRepository.save(productDetail);
        return productDetail.getId();
    }

    @Transactional
    public void updateProductDetail(ProductDetailRequest productDetailRequest, Product product) {
        ProductDetail productDetail = productDetailRepository.findAllByProduct(product).get(0);

        productDetail.update(productDetailRequest);
    }
}
