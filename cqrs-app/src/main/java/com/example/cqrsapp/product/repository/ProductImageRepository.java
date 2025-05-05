package com.example.cqrsapp.product.repository;

import com.example.cqrsapp.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
