package com.wanted.mono.domain.tag.service;

import com.wanted.mono.domain.product.entity.Product;
import com.wanted.mono.domain.tag.entity.ProductTag;
import com.wanted.mono.domain.tag.entity.Tag;
import com.wanted.mono.domain.tag.repository.ProductTagRepository;
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
public class ProductTagService {
    private final ProductTagRepository productTagRepository;
    private final TagService tagService;

    public void createProductTag(List<Long> tagIds, Product product) {
        log.info("Tag Id 리스트 조회, 크기 : {}", tagIds.size());
        List<Tag> findTagEntities = tagService.findAllById(tagIds);

        List<ProductTag> productTagEntities = new ArrayList<>();
        for (Tag findTagEntity : findTagEntities) {
            log.info("ProductTag 생성 및 연관관계 설정");
            ProductTag productTag = new ProductTag();
            productTag.addTag(findTagEntity);
            productTag.addProduct(product);

            productTagEntities.add(productTag);
        }

        log.info("ProductTag 리스트 영속성 저장");
        productTagRepository.saveAll(productTagEntities);
    }
}
