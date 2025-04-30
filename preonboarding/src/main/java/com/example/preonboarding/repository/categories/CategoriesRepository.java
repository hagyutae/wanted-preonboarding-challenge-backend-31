package com.example.preonboarding.repository.categories;

import com.example.preonboarding.domain.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories,Long> {
}
