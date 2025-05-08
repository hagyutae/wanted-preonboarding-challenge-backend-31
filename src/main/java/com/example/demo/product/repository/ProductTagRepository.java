package com.example.demo.product.repository;

import com.example.demo.product.entity.ProductTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTagEntity, Long>, ProductTagRepositoryCustom {
}
