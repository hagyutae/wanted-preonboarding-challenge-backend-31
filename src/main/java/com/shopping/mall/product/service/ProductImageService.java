package com.shopping.mall.product.service;

import com.shopping.mall.product.dto.request.ProductImageCreateRequest;
import com.shopping.mall.product.entity.Product;
import com.shopping.mall.product.entity.ProductImage;
import com.shopping.mall.product.repository.ProductImageRepository;
import com.shopping.mall.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Transactional
    public void addImage(Long productId, ProductImageCreateRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다"));

        ProductImage image = ProductImage.builder()
                .product(product)
                .url(request.getUrl())
                .altText(request.getAltText())
                .isPrimary(request.getIsPrimary())
                .displayOrder(request.getDisplayOrder())
                .optionId(request.getOptionId())
                .build();

        productImageRepository.save(image);
    }
}
