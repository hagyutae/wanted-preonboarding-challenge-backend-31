package com.wanted.ecommerce.product.dto.response;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record RelatedProductResponse(
    Long id,
    String name,
    String slug,
    String shortDescription,
    ProductImageResponse primaryImage,
    Integer basePrice,
    Integer salePrice,
    String currency
) {

    public static RelatedProductResponse of(Long id, String name, String slug,
        String shortDescription, ProductImageResponse primaryImage, BigDecimal basePrice,
        BigDecimal salePrice, String currency){
        return RelatedProductResponse.builder()
            .id(id)
            .name(name)
            .slug(slug)
            .shortDescription(shortDescription)
            .primaryImage(primaryImage)
            .basePrice(basePrice.intValueExact())
            .salePrice(salePrice.intValue())
            .currency(currency)
            .build();
    }

}
