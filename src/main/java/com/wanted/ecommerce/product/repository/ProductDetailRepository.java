package com.wanted.ecommerce.product.repository;

import com.wanted.ecommerce.product.domain.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

}
