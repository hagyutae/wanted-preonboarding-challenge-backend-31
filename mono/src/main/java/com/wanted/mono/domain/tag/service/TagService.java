package com.wanted.mono.domain.tag.service;

import com.wanted.mono.domain.tag.entity.Tag;
import com.wanted.mono.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> findAllById(List<Long> ids) {
        return tagRepository.findAllById(ids);
    }

}
