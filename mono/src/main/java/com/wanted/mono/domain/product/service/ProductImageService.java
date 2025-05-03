package com.wanted.mono.domain.product.service;

import com.wanted.mono.domain.product.dto.request.ProductImageRequest;
import com.wanted.mono.domain.product.entity.Product;
import com.wanted.mono.domain.product.entity.ProductImage;
import com.wanted.mono.domain.product.repository.ProductImageRepository;
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
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    public void createProductImage(List<ProductImageRequest> requests, Product product) {
        List<ProductImage> productImageEntities = new ArrayList<>();
        log.info("ProductImageRequest 입력, 크기 : {}", requests.size());
        for (ProductImageRequest productImageRequest : requests) {
            log.info("ProductImage 엔티티 생성");
            ProductImage productImage = ProductImage.of(productImageRequest);

            log.info("ProductImage, Product 연관관계 설정");
            productImage.addProduct(product);
            productImageEntities.add(productImage);
        }
        log.info("ProductImage 리스트 영속성 저장");
        productImageRepository.saveAll(productImageEntities);
    }
}
