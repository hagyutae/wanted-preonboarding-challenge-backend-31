package com.wanted_preonboarding_challenge_backend.eCommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
public class SummaryDto {
    private double averageRating;
    private int totalCount;
    private Map<Integer, Long> distribution;
}
