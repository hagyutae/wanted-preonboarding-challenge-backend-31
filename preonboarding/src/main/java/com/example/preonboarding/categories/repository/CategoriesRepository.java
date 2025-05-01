package com.example.preonboarding.categories.repository;

import com.example.preonboarding.categories.domain.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories,Long> {
}
