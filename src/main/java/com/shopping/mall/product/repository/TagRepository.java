package com.shopping.mall.product.repository;

import com.shopping.mall.product.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
