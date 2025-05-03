package com.preonboarding.repository;

import com.preonboarding.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption,Long> {
}
