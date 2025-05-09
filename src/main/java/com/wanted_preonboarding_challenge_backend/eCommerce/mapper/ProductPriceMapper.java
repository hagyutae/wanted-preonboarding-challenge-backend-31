package com.wanted_preonboarding_challenge_backend.eCommerce.mapper;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.ProductPrice;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request.ProductPriceCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductPriceMapper {
    ProductPriceMapper INSTANCE = Mappers.getMapper(ProductPriceMapper.class);

    ProductPrice toEntity(ProductPriceCreateRequest productPriceCreateRequest);

}
