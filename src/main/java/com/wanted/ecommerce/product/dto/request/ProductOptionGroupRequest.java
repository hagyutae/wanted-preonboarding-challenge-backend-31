package com.wanted.ecommerce.product.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionGroupRequest {
    private String name;
    private Integer displayOrder;
    private List<ProductOptionRequest> options;
}
