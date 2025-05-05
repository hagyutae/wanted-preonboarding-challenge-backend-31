package wanted.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wanted.domain.product.dto.response.ProductRatingResponse;
import wanted.domain.review.entity.Review;
import wanted.domain.review.repository.ReviewRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ProductRatingResponse getProductRating(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);

        int count = reviews.size();
        double average = count == 0 ? 0.0 :
                reviews.stream()
                        .mapToInt(Review::getRating)
                        .average()
                        .orElse(0.0);

        Map<Integer, Integer> distribution = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            distribution.put(i, 0);
        }

        for (Review review : reviews) {
            distribution.computeIfPresent(review.getRating(), (k, v) -> v + 1);
        }

        return ProductRatingResponse.of(average, count, distribution);
    }
}
