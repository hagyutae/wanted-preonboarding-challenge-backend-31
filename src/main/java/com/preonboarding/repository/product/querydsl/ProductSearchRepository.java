package com.preonboarding.repository.product.querydsl;

import com.preonboarding.domain.Product;
import com.preonboarding.dto.request.product.ProductSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearchRepository {
    Page<Product> findProductsBySearch(Pageable pageable,ProductSearchRequestDto dto);
}
