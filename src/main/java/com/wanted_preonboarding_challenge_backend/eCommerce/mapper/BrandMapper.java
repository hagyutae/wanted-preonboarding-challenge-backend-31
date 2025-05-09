package com.wanted_preonboarding_challenge_backend.eCommerce.mapper;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Brand;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.brand.BrandDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BrandMapper {
    BrandDetailDto toBrandDetailDto(Brand brand);
}
