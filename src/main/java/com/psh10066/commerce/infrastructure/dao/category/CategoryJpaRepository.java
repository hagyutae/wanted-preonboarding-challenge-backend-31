package com.psh10066.commerce.infrastructure.dao.category;

import com.psh10066.commerce.domain.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
}
