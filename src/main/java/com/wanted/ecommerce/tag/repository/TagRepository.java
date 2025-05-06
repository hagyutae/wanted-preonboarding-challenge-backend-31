package com.wanted.ecommerce.tag.repository;

import com.wanted.ecommerce.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
