package com.june.ecommerce.dto.brand;

import com.june.ecommerce.domain.brand.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {

    private int id;
    private String name;
    private String description;
    private String logoUrl;
    private String website;

    public static BrandDto fromEntity(Brand brand) {
        return BrandDto.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .logoUrl(brand.getLogoUrl())
                .website(brand.getWebsite())
                .build();
    }
}
