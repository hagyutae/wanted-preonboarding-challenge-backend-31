package com.wanted.ecommerce.seller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanted.ecommerce.seller.domain.Seller;
import java.math.RoundingMode;
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

    public static SellerDetailResponse of(Seller seller) {
        return SellerDetailResponse.builder()
            .id(seller.getId())
            .name(seller.getName())
            .description(seller.getDescription())
            .logoUrl(seller.getLogoUrl())
            .rating(seller.getRating()
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue())
            .contactEmail(seller.getContactEmail())
            .contactPhone(seller.getContactPhone())
            .build();
    }
}
