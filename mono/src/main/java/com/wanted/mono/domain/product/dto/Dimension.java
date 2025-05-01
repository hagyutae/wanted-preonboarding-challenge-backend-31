package com.wanted.mono.domain.product.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dimension {
    private Integer width;
    private Integer height;
    private Integer depth;
}

