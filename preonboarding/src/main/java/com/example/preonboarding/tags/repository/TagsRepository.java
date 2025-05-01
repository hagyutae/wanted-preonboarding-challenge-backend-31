package com.example.preonboarding.tags.repository;

import com.example.preonboarding.tags.domain.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagsRepository extends JpaRepository<Tags, Long> {
    Optional<Tags> findById(Long id);
}
