package com.june.ecommerce.repository.category;

import com.june.ecommerce.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
