package com.example.cqrsapp.product.repository;

import com.example.cqrsapp.common.dto.ProductSummaryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryRepositoryCustom {
    Page<ProductSummaryItem> findAllByCategoryIdIn(List<Long> categoryIds, Pageable pageable);
}
