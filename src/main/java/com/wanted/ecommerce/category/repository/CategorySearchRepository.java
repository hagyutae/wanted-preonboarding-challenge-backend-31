package com.wanted.ecommerce.category.repository;

import com.wanted.ecommerce.common.dto.response.ProductItemResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategorySearchRepository {

    Page<ProductItemResponse> findAllByCategoryIds(List<Long> categoryIds, Pageable pageable);
}
