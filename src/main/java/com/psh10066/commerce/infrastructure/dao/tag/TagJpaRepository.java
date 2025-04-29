package com.psh10066.commerce.infrastructure.dao.tag;

import com.psh10066.commerce.domain.model.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagJpaRepository extends JpaRepository<Tag, Long> {
}
