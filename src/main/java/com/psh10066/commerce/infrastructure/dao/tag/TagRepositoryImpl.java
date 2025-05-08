package com.psh10066.commerce.infrastructure.dao.tag;

import com.psh10066.commerce.domain.exception.ResourceNotFoundException;
import com.psh10066.commerce.domain.model.tag.Tag;
import com.psh10066.commerce.domain.model.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    private final TagJpaRepository tagJpaRepository;

    @Override
    public Tag getById(Long id) {
        return tagJpaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tag", id));
    }
}
