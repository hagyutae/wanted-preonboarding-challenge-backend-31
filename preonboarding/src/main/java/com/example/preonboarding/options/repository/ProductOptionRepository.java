package com.example.preonboarding.options.repository;

import com.example.preonboarding.options.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}
