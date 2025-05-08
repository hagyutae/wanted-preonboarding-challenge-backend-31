package com.june.ecommerce.repository.product;

import com.june.ecommerce.domain.producttag.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProductTagRepository extends JpaRepository<ProductTag, Integer> {
    Collection<ProductTag> findByProductId(int productId);
}
