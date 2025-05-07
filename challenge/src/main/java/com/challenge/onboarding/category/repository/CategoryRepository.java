package com.challenge.onboarding.category.repository;

import com.challenge.onboarding.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
