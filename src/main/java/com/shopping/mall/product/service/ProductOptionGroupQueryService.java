package com.shopping.mall.product.service;

import com.shopping.mall.product.dto.response.ProductOptionGroupResponse;
import com.shopping.mall.product.entity.Product;
import com.shopping.mall.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductOptionGroupQueryService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductOptionGroupResponse> getOptionGroups(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        return product.getOptionGroups().stream()
                .map(ProductOptionGroupResponse::from)
                .collect(Collectors.toList());
    }
}
