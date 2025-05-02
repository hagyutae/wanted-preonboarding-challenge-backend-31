package com.example.preonboarding.categories.repository;

import com.example.preonboarding.categories.domain.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories,Long> {
    List<Categories> findByLevel(Integer level);

    List<Categories> findByParentId(Long id);
}
