package com.june.ecommerce.dto.product.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAdditionalInfoCreateDto {
    private boolean assemblyRequired;
    private String assemblyTime;
}
