package com.wanted_preonboarding_challenge_backend.eCommerce.mapper;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Category;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.category.response.CategoryTreeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CategoryMapper {
    @Mapping(target = "children", ignore = true) // 재귀 필드는 직접 매핑
    CategoryTreeResponse toDto(Category category);
}
