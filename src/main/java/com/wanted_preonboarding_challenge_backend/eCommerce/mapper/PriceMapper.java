package com.wanted_preonboarding_challenge_backend.eCommerce.mapper;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.ProductPrice;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.price.PriceDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PriceMapper {
    PriceDto toPriceDto(ProductPrice productPrice);
}
