package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionsGroupDto
{
    private Long id;
    private Long productId;
    private String name;
    private Integer displayOrder;

    private List<ProductOptionDto> options;
}
