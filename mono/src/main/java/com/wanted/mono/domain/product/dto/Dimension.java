package com.wanted.mono.domain.product.dto;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dimension {
    private Integer width;
    private Integer height;
    private Integer depth;
}

