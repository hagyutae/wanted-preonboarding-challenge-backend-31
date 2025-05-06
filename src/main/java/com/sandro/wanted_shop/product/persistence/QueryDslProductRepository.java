package com.sandro.wanted_shop.product.persistence;

import com.sandro.wanted_shop.product.dto.ProductFilterDto;
import com.sandro.wanted_shop.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryDslProductRepository {
    Page<Product> findAll(Pageable pageable, ProductFilterDto filter);
}
