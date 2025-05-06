package com.june.ecommerce.repository.product;

import com.june.ecommerce.domain.productdetail.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    Optional<ProductDetail> findByProductId(int productId);
}
