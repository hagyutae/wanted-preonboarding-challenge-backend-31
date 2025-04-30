package com.example.preonboarding.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class RatingDTO {
    private double avg;
    private Long count;
    private Map<Integer, Long> distribution;

    public RatingDTO(Double avg, Long count, Map<Integer, Long> distribution) {
        this.avg = avg;
        this.count = count;
        this.distribution = distribution;
    }
}
