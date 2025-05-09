package com.wanted_preonboarding_challenge_backend.eCommerce.mapper;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.ProductDetail;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.detail.AdditionalInfoDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.detail.DetailDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.detail.DimensionsDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.util.JsonConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DetailMapper {
    @Mapping(target = "dimensions", expression = "java(parseDimensions(detail.getDimensions()))")
    @Mapping(target = "additionalInfo", expression = "java(parseAdditionalInfo(detail.getAdditionalInfo()))")
    DetailDto toDetailDto(ProductDetail detail);

    default DimensionsDto parseDimensions(String json) {
        try {
            return  JsonConverter.fromJson(json, DimensionsDto.class);
        } catch (Exception e) {
            throw new RuntimeException("dimensions JSON 변환 실패", e);
        }
    }

    default AdditionalInfoDto parseAdditionalInfo(String json) {
        try {
            return JsonConverter.fromJson(json, AdditionalInfoDto.class);
        } catch (Exception e) {
            throw new RuntimeException("additionalInfo JSON 변환 실패", e);
        }
    }
}
