package wanted.domain.product.dto.response;

import wanted.domain.product.entity.ProductImage;

public record ProductImageResponse(
        Long id,
        String url,
        String altText,
        Boolean isPrimary,
        Integer displayOrder,
        Long optionId
) {
    public static ProductImageResponse of(ProductImage image) {
        return new ProductImageResponse(
                image.getId(),
                image.getUrl(),
                image.getAltText(),
                image.isPrimary(),
                image.getDisplayOrder(),
                image.getOption() != null ? image.getOption().getId() : null
        );
    }
}