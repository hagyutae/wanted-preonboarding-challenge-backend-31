package com.shopping.mall.product.service;

import com.shopping.mall.common.exception.ResourceNotFoundException;
import com.shopping.mall.product.dto.response.ProductDetailResponse;
import com.shopping.mall.product.entity.Product;
import com.shopping.mall.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductDetailQueryService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDetailResponse getProductDetail(Long productId) {
        Product product = productRepository.findByIdWithAll(productId)
                .orElseThrow(() -> new ResourceNotFoundException("상품을 찾을 수 없습니다."));

        return ProductDetailResponse.from(product);
    }
}
