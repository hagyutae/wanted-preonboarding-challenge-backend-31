package com.wanted.ecommerce.product.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionRequest {
    private Long optionGroupId;
    @NotBlank
    @Size(max = 100)
    private String name;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal additionalPrice;
    @Size(max = 100)
    private String sku;
    private Integer stock;
    private Integer displayOrder;
}
