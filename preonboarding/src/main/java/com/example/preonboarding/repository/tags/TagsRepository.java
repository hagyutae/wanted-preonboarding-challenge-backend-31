package com.example.preonboarding.repository.tags;

import com.example.preonboarding.domain.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagsRepository extends JpaRepository<Tags, Long> {
    Optional<Tags> findById(Long id);
}
