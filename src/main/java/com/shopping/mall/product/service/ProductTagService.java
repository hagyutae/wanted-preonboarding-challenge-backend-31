package com.shopping.mall.product.service;

import com.shopping.mall.common.entity.Tag;
import com.shopping.mall.common.repository.TagRepository;
import com.shopping.mall.product.dto.request.ProductTagCreateRequest;
import com.shopping.mall.product.entity.Product;
import com.shopping.mall.product.entity.ProductTag;
import com.shopping.mall.product.repository.ProductRepository;
import com.shopping.mall.product.repository.ProductTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductTagService {

    private final ProductRepository productRepository;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;

    @Transactional
    public void addTags(Long productId, ProductTagCreateRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다"));

        for (Long tagId : request.getTagIds()) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new IllegalArgumentException("태그가 존재하지 않습니다"));

            ProductTag productTag = ProductTag.builder()
                    .product(product)
                    .tag(tag)
                    .build();

            productTagRepository.save(productTag);
        }
    }
}
