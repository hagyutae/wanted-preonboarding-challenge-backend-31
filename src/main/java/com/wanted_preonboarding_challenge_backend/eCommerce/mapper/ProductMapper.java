package com.wanted_preonboarding_challenge_backend.eCommerce.mapper;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Product;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request.ProductCreateRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.response.ProductCreateResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.response.ProductUpdateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductCreateResponse toProductCreateResponse(Product product);
    ProductUpdateResponse toProductUpdateResponse(Product product);

    Product toEntity(ProductCreateRequest request);

}
