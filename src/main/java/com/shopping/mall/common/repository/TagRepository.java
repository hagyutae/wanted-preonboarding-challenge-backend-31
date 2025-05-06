package com.shopping.mall.common.repository;

import com.shopping.mall.common.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
