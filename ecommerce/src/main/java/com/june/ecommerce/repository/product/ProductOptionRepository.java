package com.june.ecommerce.repository.product;

import com.june.ecommerce.domain.productoption.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer> {
    List<ProductOption> findByOptionGroupId(int optionGroupId);
}
