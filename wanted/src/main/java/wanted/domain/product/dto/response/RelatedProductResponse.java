package wanted.domain.product.dto.response;


import wanted.domain.product.entity.Product;
import wanted.domain.product.entity.ProductImage;

import java.math.BigDecimal;

public record RelatedProductResponse(
        Long id,
        String name,
        String slug,
        String shortDescription,
        PrimaryImage primaryImage,
        BigDecimal basePrice,
        BigDecimal salePrice,
        String currency
) {
    public static RelatedProductResponse of(Product product) {
        ProductImage mainImage = product.getImages().stream()
                .filter(ProductImage::isPrimary)
                .findFirst()
                .orElse(null);

        return new RelatedProductResponse(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getShortDescription(),
                mainImage != null ? new PrimaryImage(mainImage.getUrl(), mainImage.getAltText()) : null,
                product.getProductPrice().getBasePrice(),
                product.getProductPrice().getSalePrice(),
                product.getProductPrice().getCurrency()
        );
    }
    public record PrimaryImage(
            String url,
            String altText
    ) {}
}