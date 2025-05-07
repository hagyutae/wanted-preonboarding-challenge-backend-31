package com.psh10066.commerce.infrastructure.dao.category;

import com.psh10066.commerce.domain.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByLevel(Integer level);
}
