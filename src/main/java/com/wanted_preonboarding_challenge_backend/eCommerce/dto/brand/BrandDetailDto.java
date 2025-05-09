package com.wanted_preonboarding_challenge_backend.eCommerce.dto.brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandDetailDto {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String logoUrl;
    private String website;
}
