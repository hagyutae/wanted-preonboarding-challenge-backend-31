package com.wanted.ecommerce.brand.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BrandDetailResponse(
    Long id,
    String name,
    String description,
    String logoUrl,
    String website
){
    public static BrandDetailResponse of(Long id, String name, String description, String logoUrl, String website){
        return BrandDetailResponse.builder()
            .id(id)
            .name(name)
            .description(description)
            .logoUrl(logoUrl)
            .website(website)
            .build();
    }
}
