package com.example.demo.productoption.repository;

import com.example.demo.productoption.entity.ProductOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOptionEntity, Long>, ProductOptionRepositoryCustom {
}
