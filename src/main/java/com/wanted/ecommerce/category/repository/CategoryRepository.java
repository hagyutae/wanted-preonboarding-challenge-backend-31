package com.wanted.ecommerce.category.repository;

import com.wanted.ecommerce.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
