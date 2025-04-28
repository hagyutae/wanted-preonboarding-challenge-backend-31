package com.wanted.ecommerce.seller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SellerDetailResponse(
    Long id,
    String name,
    String description,
    String logoUrl,
    Double rating,
    String contactEmail,
    String contactPhone
) {

    public static SellerDetailResponse of(Long id, String name, String description, String logoUrl,
        Double rating, String contactEmail, String contactPhone) {
        return SellerDetailResponse.builder()
            .id(id)
            .name(name)
            .description(description)
            .logoUrl(logoUrl)
            .rating(rating)
            .contactEmail(contactEmail)
            .contactPhone(contactPhone)
            .build();
    }
}
