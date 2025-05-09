package com.wanted_preonboarding_challenge_backend.eCommerce.mapper;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Seller;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.seller.SellerDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SellerMapper {
    SellerDetailDto toSellerDetailDto(Seller seller);
}
