package com.wanted_preonboarding_challenge_backend.eCommerce.dto.seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SellerDto {
    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private Double rating;
}