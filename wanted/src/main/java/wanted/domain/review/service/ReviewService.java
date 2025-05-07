package wanted.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.common.exception.CustomException;
import wanted.common.exception.code.GlobalExceptionCode;
import wanted.domain.product.dto.response.Pagination;
import wanted.domain.product.dto.response.ProductRatingResponse;
import wanted.domain.product.entity.Product;
import wanted.domain.product.repository.ProductRepository;
import wanted.domain.review.ReviewSearchCondition;
import wanted.domain.review.dto.request.ProductReviewRequest;
import wanted.domain.review.dto.response.ProductReviewResponse;
import wanted.domain.review.dto.response.ReviewResponse;
import wanted.domain.review.dto.response.ReviewUserResponse;
import wanted.domain.review.dto.response.SummaryResponse;
import wanted.domain.review.entity.Review;
import wanted.domain.review.repository.ReviewRepository;
import wanted.domain.user.entity.User;
import wanted.domain.user.repository.UserRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

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

    @Transactional(readOnly = true)
    public ProductReviewResponse getProductReviews(Long productId, ReviewSearchCondition condition) {
        int page = Optional.ofNullable(condition.page()).orElse(1);
        int perPage = Optional.ofNullable(condition.perPage()).orElse(10);
        int offset = (page - 1) * perPage;

        String sort = Optional.ofNullable(condition.sort()).orElse("created_at:desc");
        String[] sortParts = sort.split(":");
        String sortBy = sortParts[0];
        boolean isDesc = sortParts.length < 2 || "desc".equalsIgnoreCase(sortParts[1]);

        List<Review> reviews = reviewRepository.findPagedByProductId(
                productId, condition.rating(), sortBy, isDesc, offset, perPage);
        int totalItems = reviewRepository.countByProductIdAndRating(productId, condition.rating());

        List<ReviewResponse> items = reviews.stream()
                .map(review -> new ReviewResponse(
                        review.getId(),
                        review.getUser() == null ? null : new ReviewUserResponse(
                                review.getUser().getId(),
                                review.getUser().getName(),
                                review.getUser().getAvatarUrl()
                        ),
                        review.getRating(),
                        review.getTitle(),
                        review.getContent(),
                        review.getCreatedAt(),
                        review.getUpdatedAt(),
                        review.isVerifiedPurchase(),
                        review.getHelpfulVotes()
                ))
                .toList();

        List<Review> allReviews = reviewRepository.findByProductId(productId);

        BigDecimal averageRating = allReviews.isEmpty()
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(allReviews.stream()
                        .mapToInt(Review::getRating)
                        .average().orElse(0.0))
                .setScale(1, BigDecimal.ROUND_HALF_UP);

        int totalCount = allReviews.size();
        Map<String, Integer> distribution = new LinkedHashMap<>();
        for (int i = 5; i >= 1; i--) {
            int finalI = i;
            int count = (int) allReviews.stream()
                    .filter(r -> r.getRating() == finalI)
                    .count();
            distribution.put(String.valueOf(i), count);
        }

        SummaryResponse summary = new SummaryResponse(averageRating, totalCount, distribution);
        Pagination pagination = new Pagination(totalItems,
                (int) Math.ceil((double) totalItems / perPage), page, perPage);

        return new ProductReviewResponse(items, summary, pagination);
    }

    @Transactional
    public ReviewResponse createProductReview(Long productId, ProductReviewRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Product", productId)));

        //시큐리티 생략으로 인한 임의 유저 선택
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("User", 1L)));

        Review review = Review.from(request, user, product);
        reviewRepository.save(review);

        return ReviewResponse.of(review);
    }

    private Map<String, Object> resourceNotFoundDetails(String type, Object id) {
        return Map.of("resourceType", type, "resourceId", id);
    }
}
