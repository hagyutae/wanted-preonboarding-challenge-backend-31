package com.wanted.mono.domain.category.repository;

import com.wanted.mono.domain.category.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @EntityGraph(attributePaths = "productCategories")
    List<Category> findAllByIdIn(List<Long> categoryIds);

    List<Category> findCategoriesByLevel(Integer level);
}
