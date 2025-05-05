package com.preonboarding.domain;

import com.preonboarding.dto.request.review.ProductReviewRequestDto;
import com.preonboarding.dto.response.review.ReviewSummaryResponse;
import com.preonboarding.global.code.ErrorCode;
import com.preonboarding.global.response.BaseException;
import com.preonboarding.global.response.ErrorResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Integer rating;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "verified_purchase")
    private Boolean verifiedPurchase;

    @Column(name = "helpful_votes")
    private Integer helpfulVotes;

    public static Review of(ProductReviewRequestDto dto) {
        return Review.builder()
                .rating(dto.getRating())
                .title(dto.getTitle())
                .content(dto.getContent())
                .verifiedPurchase(true)
                .helpfulVotes(0)
                .build();
    }

    public void updateUser(User user) {
        if (this.user != null) {
            this.user.getReviewList().remove(this);
        }

        this.user = user;
        user.getReviewList().add(this);
    }

    public void updateProduct(Product product) {
        if (this.product != null) {
            this.product.getReviewList().remove(this);
        }

        this.product = product;
        product.getReviewList().add(this);
    }

    public void updateReview(ProductReviewRequestDto dto) {
        this.rating = dto.getRating();
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void validateUser(User user) {
        if (this.user==null || !this.user.equals(user)) {
            throw new BaseException(false, ErrorResponseDto.of(ErrorCode.REVIEW_FORBIDDEN));
        }
    }

    public static ReviewSummaryResponse createSummaryResponse(List<Review> fetchedReviewList) {
        double averageRating = calculateAverageRating(fetchedReviewList);
        int totalFetchedCount = getFetchedReviewCount(fetchedReviewList);

        ReviewSummaryResponse.DistributionDto distributionDto = calculateRatingDistribution(fetchedReviewList);

        return ReviewSummaryResponse.builder()
                .averageRating(averageRating)
                .totalCount(totalFetchedCount)
                .distribution(distributionDto)
                .build();
    }

    public static double calculateAverageRating(List<Review> fetchedReviewList) {
        if (fetchedReviewList.isEmpty()) return 0.0;

        double average = fetchedReviewList.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        return BigDecimal.valueOf(average)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public static int getFetchedReviewCount(List<Review> fetchedReviewList) {
        return fetchedReviewList.size();
    }

    private static ReviewSummaryResponse.DistributionDto calculateRatingDistribution(List<Review> fetchedReviewList) {
        int[] ratingCounts = new int[6];

        fetchedReviewList.forEach(review -> {
            int rating = review.getRating();
            ratingCounts[rating] ++;
        });

        return ReviewSummaryResponse.DistributionDto.builder()
                .rating5(ratingCounts[5])
                .rating4(ratingCounts[4])
                .rating3(ratingCounts[3])
                .rating2(ratingCounts[2])
                .rating1(ratingCounts[1])
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review that = (Review) o;
        if (id == null && that.id == null) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
