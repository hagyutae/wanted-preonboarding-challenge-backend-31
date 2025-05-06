package com.june.ecommerce.dto.product.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionGroupCreateDto {
    private String name;
    private int displayOrder;
    private List<ProductOptionCreateDto> options;
}
