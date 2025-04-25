package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.tag;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
