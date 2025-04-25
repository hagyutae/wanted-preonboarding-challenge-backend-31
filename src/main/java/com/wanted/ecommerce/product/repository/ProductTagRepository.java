package com.wanted.ecommerce.product.repository;

import com.wanted.ecommerce.product.domain.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {

}
