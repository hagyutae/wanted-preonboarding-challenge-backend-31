package com.example.preonboarding.repository.products;

import com.example.preonboarding.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products,Long> {
}
