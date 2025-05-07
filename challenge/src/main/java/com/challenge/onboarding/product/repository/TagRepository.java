package com.challenge.onboarding.product.repository;

import com.challenge.onboarding.product.domain.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
