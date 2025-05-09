package com.wanted.mono.domain.product.repository;

import com.wanted.mono.domain.product.entity.Product;
import com.wanted.mono.domain.product.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findAllByProduct(Product product);
}
