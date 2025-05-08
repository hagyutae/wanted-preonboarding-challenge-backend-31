package com.psh10066.commerce.domain.model.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RequiredArgsConstructor
public class ReviewFirstCollection {

    private final List<Review> reviews;

    public boolean isNullOrEmpty() {
        return reviews == null || reviews.isEmpty();
    }

    public BigDecimal getAverage() {
        if (reviews.isEmpty()) {
            return null;
        }
        return BigDecimal.valueOf(reviews.stream().mapToInt(Review::getRating).average().getAsDouble()).setScale(1, RoundingMode.HALF_UP);
    }

    public int getSize() {
        return reviews.size();
    }

    public Distribution getDistribution() {
        int[] ratings = new int[6];
        for (Review review : reviews) {
            Integer rating = review.getRating();
            if (rating >= 1 && rating <= 5) {
                ratings[rating]++;
            }
        }
        return new Distribution(ratings[5], ratings[4], ratings[3], ratings[2], ratings[1]);
    }

    public record Distribution(
        @JsonProperty("5")
        Integer five,
        @JsonProperty("4")
        Integer four,
        @JsonProperty("3")
        Integer three,
        @JsonProperty("2")
        Integer two,
        @JsonProperty("1")
        Integer one
    ) {
    }
}
