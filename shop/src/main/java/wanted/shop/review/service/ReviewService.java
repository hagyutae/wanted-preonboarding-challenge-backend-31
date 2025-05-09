package wanted.shop.review.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.shop.common.api.PaginatedData;
import wanted.shop.common.api.Pagination;
import wanted.shop.product.domain.ProductId;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.domain.entity.ReviewId;
import wanted.shop.review.domain.query.ReviewPageRequest;
import wanted.shop.review.domain.query.ReviewSpecification;
import wanted.shop.review.dto.ReviewDto;
import wanted.shop.review.dto.ReviewListResponse;
import wanted.shop.review.dto.ReviewSummaryDto;
import wanted.shop.review.respository.ReviewRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ReviewListResponse getReviewsByProductId(ProductId productId, ReviewPageRequest request) {

        Specification<Review> spec = request.getRating()
                .map(rating -> ReviewSpecification.withFilters(productId, rating))
                .orElse(ReviewSpecification.withFilters(productId));

        Page<Review> result = reviewRepository.findAll(spec, request.toPageable());

        Pagination pagination = Pagination.from(result);

        List<ReviewDto> reviewDtoList = result.getContent().stream()
                .map(Review::toReviewDto)
                .toList();
        ReviewSummaryDto reviewSummaryDto = ReviewSummaryDto.from(result.getContent());

        return new ReviewListResponse(reviewDtoList, reviewSummaryDto, pagination);
    }

    @Transactional
    public void deleteReview(ReviewId reviewId) {
        Review existingReview = reviewRepository.findById(reviewId);
        existingReview.delete();
        reviewRepository.save(existingReview);
    }
}
