package com.wanted_preonboarding_challenge_backend.eCommerce.mapper;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.ProductCategory;
import com.wanted_preonboarding_challenge_backend.eCommerce.domain.ProductOptionGroup;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.category.response.CategoryResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request.ProductOptionGroupCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductOptionGroupMapper {
    ProductOptionGroupMapper INSTANCE = Mappers.getMapper(ProductOptionGroupMapper.class);
    ProductOptionGroup toEntity(ProductOptionGroupCreateRequest dto);

}
