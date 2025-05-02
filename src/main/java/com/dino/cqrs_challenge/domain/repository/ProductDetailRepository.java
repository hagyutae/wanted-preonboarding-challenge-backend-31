package com.dino.cqrs_challenge.domain.repository;

import com.dino.cqrs_challenge.domain.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    ProductDetail findByProductId(Long productId);

}
