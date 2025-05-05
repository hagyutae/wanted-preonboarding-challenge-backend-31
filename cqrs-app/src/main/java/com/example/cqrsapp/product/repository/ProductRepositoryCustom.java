package com.example.cqrsapp.product.repository;

import com.example.cqrsapp.common.dto.ProductSummaryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<ProductSummaryItem> findAll(SearchParm searchParm, Pageable pageable);
}
