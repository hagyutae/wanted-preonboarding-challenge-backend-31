package com.wanted.mono.domain.brand.service;

import com.wanted.mono.domain.brand.entity.Brand;
import com.wanted.mono.domain.brand.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public Brand findById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }
}

