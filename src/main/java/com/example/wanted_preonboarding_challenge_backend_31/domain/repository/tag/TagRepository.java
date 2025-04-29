package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.tag;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.tag.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select t from Tag t join ProductTag pt on pt.tag.id = t.id and pt.id in :productTagIds")
    List<Tag> findAllByProductTagIdIn(@Param("productTagIds") List<Long> productTagIds);
}
