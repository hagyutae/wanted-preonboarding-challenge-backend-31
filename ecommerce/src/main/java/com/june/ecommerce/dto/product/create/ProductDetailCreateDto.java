package com.june.ecommerce.dto.product.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailCreateDto {
    private double weight;
    private DimensionsCreateDto dimensions;
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    private ProductAdditionalInfoCreateDto additionalInfo;
}
