package com.wanted.ecommerce.brand.service;

import com.wanted.ecommerce.brand.domain.Brand;
import com.wanted.ecommerce.brand.dto.response.BrandDetailResponse;
import com.wanted.ecommerce.brand.dto.response.BrandResponse;

public interface BrandService {

    Brand getBrandById(Long brandId);

    BrandResponse createBrandResponse(Brand brand);
    BrandDetailResponse createBrandDetailResponse(Brand brand);
}
