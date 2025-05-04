package investLee.platform.ecommerce.service;

import investLee.platform.ecommerce.domain.entity.ProductEntity;
import investLee.platform.ecommerce.domain.entity.ReviewEntity;
import investLee.platform.ecommerce.dto.request.ReviewCreateRequest;
import investLee.platform.ecommerce.dto.response.ReviewResponse;
import investLee.platform.ecommerce.dto.request.ReviewUpdateRequest;
import investLee.platform.ecommerce.exception.ReviewPermissionException;
import investLee.platform.ecommerce.repository.ProductRepository;
import investLee.platform.ecommerce.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long createReview(Long productId, ReviewCreateRequest dto) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품 없음"));

        ReviewEntity review = ReviewEntity.builder()
                .product(product)
                .userId(dto.getUserId())
                .rating(dto.getRating())
                .title(dto.getTitle())
                .content(dto.getContent())
                .verifiedPurchase(Boolean.TRUE.equals(dto.getVerifiedPurchase()))
                .helpfulVotes(0)
                .build();

        reviewRepository.save(review);
        return review.getId();
    }

    public Page<ReviewResponse> getReviews(Long productId, int page, int size) {
        Page<ReviewEntity> entities = reviewRepository.findByProductId(productId, PageRequest.of(page, size));

        return entities.map(r -> ReviewResponse.builder()
                .id(r.getId())
                .rating(r.getRating())
                .title(r.getTitle())
                .content(r.getContent())
                .verifiedPurchase(r.isVerifiedPurchase())
                .helpfulVotes(r.getHelpfulVotes())
                .createdAt(r.getCreatedAt())
                .userId(r.getUserId())
                .build());
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewUpdateRequest dto) {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("리뷰 없음"));

        if (!review.getUserId().equals(dto.getUserId())) {
            throw new ReviewPermissionException("리뷰 수정 권한 없음");
        }

        review.update(dto.getTitle(), dto.getContent(), dto.getRating());

        // save 생략 가능 (JPA dirty checking)
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("리뷰 없음"));

        if (!review.getUserId().equals(userId)) {
            throw new ReviewPermissionException("리뷰 삭제 권한 없음");
        }

        reviewRepository.delete(review);
    }
}