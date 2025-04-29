package com.psh10066.commerce.infrastructure.dao.brand;

import com.psh10066.commerce.domain.exception.ResourceNotFoundException;
import com.psh10066.commerce.domain.model.brand.Brand;
import com.psh10066.commerce.domain.model.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BrandRepositoryImpl implements BrandRepository {

    private final BrandJpaRepository brandJpaRepository;

    @Override
    public Brand getById(Long id) {
        return brandJpaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Brand", id));
    }
}
