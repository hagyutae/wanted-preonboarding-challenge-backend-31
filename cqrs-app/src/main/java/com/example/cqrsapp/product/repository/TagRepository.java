package com.example.cqrsapp.product.repository;

import com.example.cqrsapp.product.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByIdIn(List<Long> ids);
}
