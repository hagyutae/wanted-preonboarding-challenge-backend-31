package com.dino.cqrs_challenge.domain.repository;

import com.dino.cqrs_challenge.domain.entity.Product;
import com.dino.cqrs_challenge.presentation.model.rq.ProductSearchCondition;
import org.springframework.data.domain.Page;

public interface ProductRepositoryCustom {

    Page<Product> searchProducts(ProductSearchCondition condition);
}
