package com.wanted_preonboarding_challenge_backend.eCommerce.mapper;


import com.wanted_preonboarding_challenge_backend.eCommerce.domain.ProductOption;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.option.request.ProductOptionAddRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.option.response.ProductOptionAddResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.option.response.ProductOptionUpdateResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request.OptionCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductOptionMapper {
    ProductOptionMapper INSTANCE = Mappers.getMapper(ProductOptionMapper.class);

    ProductOption toEntity(OptionCreateRequest dto);
    ProductOption toEntity(ProductOptionAddRequest request);

    @Mapping(target = "optionGroupId", source = "optionGroup.id")
    ProductOptionAddResponse toProductOptionAddResponse(ProductOption productOption);
    @Mapping(target = "optionGroupId", source = "optionGroup.id")
    ProductOptionUpdateResponse toProductOptionUpdateResponse(ProductOption productOption);



}
