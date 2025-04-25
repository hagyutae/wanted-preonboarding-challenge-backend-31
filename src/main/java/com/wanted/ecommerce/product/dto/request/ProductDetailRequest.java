package com.wanted.ecommerce.product.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequest {
    @Digits(integer = 8, fraction = 2)
    private BigDecimal weight;
    private DimensionsRequest dimensionsRequest;
    private String materials;
    @Size(max = 100)
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    private AdditionalInfoRequest additionalInfoRequest;
}


