package com.dino.cqrs_challenge.domain.repository;

import com.dino.cqrs_challenge.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
