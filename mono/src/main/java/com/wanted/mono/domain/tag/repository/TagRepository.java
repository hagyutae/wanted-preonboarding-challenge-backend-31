package com.wanted.mono.domain.tag.repository;

import com.wanted.mono.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
