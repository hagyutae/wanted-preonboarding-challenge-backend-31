package wanted.shop.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import wanted.shop.review.domain.entity.Review;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Builder
public class ReviewSummaryDto {

    @JsonProperty("average_rating")
    private final double averageRating;

    @JsonProperty("total_count")
    private final int totalCount;

    @JsonProperty("distribution")
    private final Map<Integer, Long> ratingDistribution;

    public static ReviewSummaryDto from(List<Review> reviews) {

        double ratingAverage = reviews.stream()
                .mapToInt(review -> review.getReviewData().getRating())
                .average()
                .orElse(0.0);

        Map<Integer, Long> ratingDistribution = IntStream.rangeClosed(1, 5)
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        i -> 0L,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        Map<Integer, Long> counted = reviews.stream()
                .map(review -> review.getReviewData().getRating())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        ratingDistribution.putAll(counted);

        return ReviewSummaryDto.builder()
                .averageRating(ratingAverage)
                .totalCount(reviews.size())
                .ratingDistribution(ratingDistribution)
                .build();
    }



}