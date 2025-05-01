package com.wanted.mono.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ProductOptionGroupRequest {
    private String name;
    @JsonProperty("display_order")
    private Integer displayOrder;
    private List<ProductOptionRequest> options;
}
