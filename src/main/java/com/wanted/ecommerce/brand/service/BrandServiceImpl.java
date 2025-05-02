package com.wanted.ecommerce.brand.service;

import com.wanted.ecommerce.brand.domain.Brand;
import com.wanted.ecommerce.brand.dto.response.BrandDetailResponse;
import com.wanted.ecommerce.brand.dto.response.BrandResponse;
import com.wanted.ecommerce.brand.repository.BrandRepository;
import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Transactional(readOnly = true)
    @Override
    public Brand getBrandById(Long brandId) {
        return brandRepository.findById(brandId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));
    }

    @Override
    public BrandResponse createBrandResponse(Long brandId, String brandName) {
        return BrandResponse.of(brandId, brandName);
    }

    @Override
    public BrandDetailResponse createBrandDetailResponse(Brand brand) {
        return BrandDetailResponse.of(brand.getId(),
            brand.getName(), brand.getDescription(), brand.getLogoUrl(), brand.getWebsite());
    }
}
