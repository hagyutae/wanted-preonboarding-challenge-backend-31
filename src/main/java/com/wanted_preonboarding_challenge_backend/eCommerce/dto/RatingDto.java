package com.wanted_preonboarding_challenge_backend.eCommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class RatingDto {
    private Double average;
    private Integer count;
    private Map<String, Integer> distribution;
}