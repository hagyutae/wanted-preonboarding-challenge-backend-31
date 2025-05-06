package com.shopping.mall.product.service;

import com.shopping.mall.product.dto.request.ProductSearchCondition;
import com.shopping.mall.product.dto.response.ProductSimpleResponse;
import com.shopping.mall.product.entity.Product;
import com.shopping.mall.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductSimpleResponse> getProducts(ProductSearchCondition condition) {

        List<Product> products = productRepository.findByCondition(
                condition.getStatus(),
                condition.getMinPrice(),
                condition.getMaxPrice(),
                condition.getSellerId(),
                condition.getBrandId(),
                condition.getInStock(),
                condition.getTagIds()
        );

        return products.stream()
                .map(ProductSimpleResponse::from)
                .toList();
    }
}
