package com.wanted.mono.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RatingDto {
    private Double average;
    private Long count;
    private Map<String, Integer> distribution;
}
