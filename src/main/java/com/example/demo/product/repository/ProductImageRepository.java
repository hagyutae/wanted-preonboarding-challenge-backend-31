package com.example.demo.product.repository;

import com.example.demo.product.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long>, ProductImageRepositoryCustom {
}
