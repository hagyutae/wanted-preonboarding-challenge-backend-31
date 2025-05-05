package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductTag;
import com.wanted.ecommerce.product.repository.ProductTagRepository;
import com.wanted.ecommerce.product.service.ProductTagService;
import com.wanted.ecommerce.tag.domain.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductTagServiceImpl implements ProductTagService {
    private final ProductTagRepository productTagRepository;

    @Transactional
    @Override
    public List<ProductTag> saveAllProductTags(Product product, List<Tag> tags){
        List<ProductTag> productTags = tags.stream().map(tag->
            ProductTag.of(product, tag)).toList();
        return productTagRepository.saveAll(productTags);
    }
}
