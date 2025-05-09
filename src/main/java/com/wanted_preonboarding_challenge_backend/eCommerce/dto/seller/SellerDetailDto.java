package com.wanted_preonboarding_challenge_backend.eCommerce.dto.seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerDetailDto {
    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private Double rating;
    private String contactEmail;
    private String contactPhone;
}
