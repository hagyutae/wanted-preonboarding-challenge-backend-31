package com.june.ecommerce.repository.product;

import com.june.ecommerce.domain.producttag.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag, Integer> {
}
