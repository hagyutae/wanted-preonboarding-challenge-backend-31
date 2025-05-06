package minseok.cqrschallenge.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import minseok.cqrschallenge.common.exception.ForbiddenException;
import minseok.cqrschallenge.common.exception.ResourceNotFoundException;
import minseok.cqrschallenge.product.entity.Product;
import minseok.cqrschallenge.product.repository.ProductRepository;
import minseok.cqrschallenge.review.dto.request.ReviewCreateRequest;
import minseok.cqrschallenge.review.dto.request.ReviewUpdateRequest;
import minseok.cqrschallenge.review.dto.response.ReviewListResponse;
import minseok.cqrschallenge.review.dto.response.ReviewResponse;
import minseok.cqrschallenge.review.dto.response.ReviewUpdateResponse;
import minseok.cqrschallenge.review.entity.Review;
import minseok.cqrschallenge.review.mapper.ReviewMapper;
import minseok.cqrschallenge.review.repository.ReviewRepository;
import minseok.cqrschallenge.user.entity.User;
import minseok.cqrschallenge.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional(readOnly = true)
    public ReviewListResponse getProductReviews(Long productId, int page, int perPage, String sort, Integer rating) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다."));

        Pageable pageable = createPageable(page, perPage, sort);

        Page<Review> reviewPage;
        if (rating != null) {
            reviewPage = reviewRepository.findByProductIdAndRating(productId, rating, pageable);
        } else {
            reviewPage = reviewRepository.findByProductIdWithPage(productId, pageable);
        }

        Double averageRating = reviewRepository.calculateAverageRating(productId);
        Integer totalCount = reviewRepository.countByProductId(productId);
        List<Object[]> ratingDistribution = reviewRepository.countByRatingForProduct(productId);

        return reviewMapper.toListResponse(reviewPage, page, perPage, productId);
    }

    @Override
    @Transactional
    public ReviewResponse createReview(Long productId, Long userId, ReviewCreateRequest request) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("사용자 정보를 찾을 수 없습니다."));

        Review review = Review.builder()
            .product(product)
            .user(user)
            .rating(request.getRating())
            .title(request.getTitle())
            .content(request.getContent())
            .verifiedPurchase(true)
            .helpfulVotes(0)
            .build();

        Review savedReview = reviewRepository.save(review);

        return reviewMapper.toResponse(savedReview);
    }

    @Override
    @Transactional
    public ReviewUpdateResponse updateReview(Long reviewId, Long userId, ReviewUpdateRequest request) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ResourceNotFoundException("요청한 리뷰를 찾을 수 없습니다."));

        if (!review.getUser().getId().equals(userId)) {
            throw new ForbiddenException("다른 사용자의 리뷰를 수정할 권한이 없습니다.");
        }

        review.update(request.getRating(), request.getTitle(), request.getContent());

        return reviewMapper.toUpdateResponse(review);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ResourceNotFoundException("요청한 리뷰를 찾을 수 없습니다."));

        if (!review.getUser().getId().equals(userId)) {
            throw new ForbiddenException("다른 사용자의 리뷰를 삭제할 권한이 없습니다.");
        }
        reviewRepository.delete(review);
    }

    private Pageable createPageable(int page, int perPage, String sort) {
        String[] sortParts = sort.split(":");
        String sortField = convertSortField(sortParts[0]);
        Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("asc")
            ? Sort.Direction.ASC : Sort.Direction.DESC;

        return PageRequest.of(page - 1, perPage, Sort.by(direction, sortField));
    }

    private String convertSortField(String fieldName) {
        return switch (fieldName) {
            case "created_at" -> "createdAt";
            case "updated_at" -> "updatedAt";
            case "helpful_votes" -> "helpfulVotes";
            default -> fieldName;
        };
    }
}
