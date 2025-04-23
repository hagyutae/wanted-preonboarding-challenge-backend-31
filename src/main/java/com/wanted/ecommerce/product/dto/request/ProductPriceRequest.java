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
public class ProductPriceRequest {
    @Digits(integer = 10, fraction = 2)
    private BigDecimal basePrice;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal salePrice;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal costPrice;
    @Size(max = 3)
    private String currency;
    @Digits(integer = 3, fraction = 2)
    private BigDecimal taxRate;
}
