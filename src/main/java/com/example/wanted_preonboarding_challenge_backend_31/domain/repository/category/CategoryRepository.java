package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.category;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
