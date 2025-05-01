package com.wanted.ecommerce.review.service;

import com.wanted.ecommerce.review.domain.Review;
import com.wanted.ecommerce.review.dto.response.RatingResponse;
import com.wanted.ecommerce.review.repository.ReviewRepository;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    @Override
    public double getAvgRatingByProductId(Long productId) {
        return Optional.ofNullable(
                reviewRepository.findAvgRatingByProductId(productId))
            .orElse(0.0);
    }

    @Override
    public Integer getReviewCountByProductId(Long productId) {
        return Optional.ofNullable(
                reviewRepository.findReviewCountByProductId(productId))
            .orElse(0L)
            .intValue();
    }

    @Override
    public List<Review> getReviews(Long productId) {
        return reviewRepository.findReviewsByProductId(productId);
    }

    @Override
    public RatingResponse createRatingResponse(Long productId) {
        double average = getAvgRatingByProductId(productId);
        List<Review> reviews = getReviews(productId);
        Map<Integer, Long> rating = IntStream.rangeClosed(1, 5)
            .boxed()
            .collect(Collectors.toMap(Function.identity(), i -> 0L));

        rating.putAll(reviews.stream()
            .collect(Collectors.groupingBy(
                Review::getRating,
                Collectors.counting()
            )));

        Map<Integer, Long> sortedRating = rating.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
            .collect(Collectors.toMap(
                Entry::getKey,
                Entry::getValue,
                (existing, replacement) -> existing,
                LinkedHashMap::new
            ));
        return RatingResponse.of(average, reviews.size(), sortedRating);
    }
}
