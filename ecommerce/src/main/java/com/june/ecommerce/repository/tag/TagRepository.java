package com.june.ecommerce.repository.tag;

import com.june.ecommerce.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {

}
