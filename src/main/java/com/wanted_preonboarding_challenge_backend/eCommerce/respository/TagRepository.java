package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
