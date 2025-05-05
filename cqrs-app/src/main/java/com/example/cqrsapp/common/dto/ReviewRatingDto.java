package com.example.cqrsapp.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewRatingDto {

    private Double average;
    private Long count;
    @Setter
    private Map<String, Long> distribution;

    public ReviewRatingDto(Double average, Long count) {
        this.average = average;
        this.count = count;
    }

    public static ReviewRatingDto of(Double average, Long count, Map<String, Long> distribution) {
        return ReviewRatingDto.builder()
                .average(average)
                .count(count)
                .distribution(distribution)
                .build();
    }


    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DistributionDto {
        String rating;
        Long count;

        public  DistributionDto(Integer rating, Long count) {
            this.rating = String.valueOf(rating);
            this.count = count;
        }
    }
}
