package com.example.demo.product.repository;

import com.example.demo.product.dto.ProductQueryFilter;
import com.example.demo.product.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface ProductRepositoryCustom {
    Page<ProductEntity> findProductSummaryPage(ProductQueryFilter productQueryFilter, Pageable pageable);

    Boolean existBySlug(String slug);

    List<ProductEntity> findTop10LatestList();

    List<ProductEntity> findAllByIds(Collection<Long> ids);
}
