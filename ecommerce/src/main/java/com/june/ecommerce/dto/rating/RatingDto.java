package com.june.ecommerce.dto.rating;

import com.june.ecommerce.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {
    private double average;
    private int count;
    private Map<Integer, Long> distribution;

    public static RatingDto fromReviews(List<Review> reviews) {
        int count = reviews.size();
        double average = count == 0 ? 0.0
                : reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        Map<Integer, Long> ditribution = reviews.stream()
                .collect(Collectors.groupingBy(
                        Review::getRating,
                        Collectors.counting()
                ));

        return RatingDto.builder()
                .average(Math.round(average * 100) / 100.0)
                .count(count)
                .distribution(ditribution)
                .build();
    }
}
