package com.example.demo.productoption.repository;

import com.example.demo.productoption.entity.ProductOptionGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroupEntity, Long>, ProductOptionGroupRepositoryCustom {
}
