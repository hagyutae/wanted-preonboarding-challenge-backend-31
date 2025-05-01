package com.wanted.mono.domain.product.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductOptionGroupRequest {
    private String name;
    private Integer displayOrder;
    private List<ProductOptionRequest> options;
}
