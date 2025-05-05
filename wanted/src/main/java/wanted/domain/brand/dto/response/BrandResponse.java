package wanted.domain.brand.dto.response;

import wanted.domain.brand.entity.Brand;

public record BrandResponse(
        Long id,
        String name,
        String description,
        String logoUrl,
        String website
) {
    public static BrandResponse of(Brand brand) {
        return new BrandResponse(
                brand.getId(),
                brand.getName(),
                brand.getDescription(),
                brand.getLogoUrl(),
                brand.getWebsite()
        );
    }
}