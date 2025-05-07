package wanted.shop.review.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.shop.common.api.PaginatedData;
import wanted.shop.common.api.Pagination;
import wanted.shop.review.domain.ProductId;
import wanted.shop.review.domain.Review;
import wanted.shop.review.dto.ReviewPageRequest;
import wanted.shop.review.dto.ReviewSpecification;
import wanted.shop.review.respository.ReviewRepository;

@Service
@AllArgsConstructor
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public PaginatedData<Review> getReviewsByProductId(ProductId productId, ReviewPageRequest request) {

        Specification<Review> spec = request.getRating()
                .map(rating -> ReviewSpecification.withFilters(productId, rating))
                .orElse(ReviewSpecification.withFilters(productId));

        Page<Review> result = reviewRepository.findAll(spec, request.toPageable());

        Pagination pagination = Pagination.from(result);

        return new PaginatedData<>(result.getContent(), pagination);
    }
}
