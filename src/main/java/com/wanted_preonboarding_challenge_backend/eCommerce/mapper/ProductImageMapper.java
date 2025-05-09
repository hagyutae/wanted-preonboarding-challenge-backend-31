package com.wanted_preonboarding_challenge_backend.eCommerce.mapper;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.ProductImage;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.Image.request.ImageAddRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.Image.response.ImageAddResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.option.request.ProductOptionAddRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request.ProductImageCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductImageMapper {
    ProductImageMapper INSTANCE = Mappers.getMapper(ProductImageMapper.class);

    @Mapping(target = "option", ignore = true)
    ProductImage toEntity(ProductImageCreateRequest request);
    ProductImage toEntity(ImageAddRequest request);

    @Mapping(target = "optionId", source = "ProductOption.id")
    ImageAddResponse toImageAddResponse(ProductImage productImage);
}
