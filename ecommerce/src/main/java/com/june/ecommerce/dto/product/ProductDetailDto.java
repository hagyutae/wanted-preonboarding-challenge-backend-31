package com.june.ecommerce.dto.product;

import com.june.ecommerce.domain.product.Product;
import com.june.ecommerce.domain.productdetail.ProductDetail;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {

    private int id;
    private Product product;
    private Double weight;
    private String dimensions;
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    private String additionalInfo;

    public static ProductDetailDto fromEntity(ProductDetail entity) {
        if (entity == null) return null;
        return ProductDetailDto.builder()
                .weight(entity.getWeight())
                .dimensions(entity.getDimensions())
                .materials(entity.getMaterials())
                .countryOfOrigin(entity.getCountryOfOrigin())
                .warrantyInfo(entity.getWarrantyInfo())
                .careInstructions(entity.getCareInstructions())
                .additionalInfo(entity.getAdditionalInfo())
                .build();
    }
}
