package com.sandro.wanted_shop.review;

import com.sandro.wanted_shop.product.entity.Product;
import com.sandro.wanted_shop.product.persistence.ProductRepository;
import com.sandro.wanted_shop.review.dto.ReviewDto;
import com.sandro.wanted_shop.review.entity.ReviewRepository;
import com.sandro.wanted_shop.review.entity.User;
import com.sandro.wanted_shop.review.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long create(Long productId, CreateReviewCommand command) {
        Product product = productRepository.findById(productId)
                .orElseThrow();
        User reviewer = userRepository.findById(command.userId())
                .orElseThrow();
        return reviewRepository.save(command.toEntity(product, reviewer)).getId();
    }

    public List<ReviewDto> getAllReviews(Long productId) {
        return reviewRepository.findAllByProduct_id(productId).stream()
                .map(ReviewDto::from)
                .toList();
    }

    @Transactional
    public void update(Long id, UpdateReviewCommand command) {
        reviewRepository.findById(id)
                .ifPresent(review -> review.update(command));
    }

    @Transactional
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
