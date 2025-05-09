package com.wanted_preonboarding_challenge_backend.eCommerce.service;

import com.wanted_preonboarding_challenge_backend.eCommerce.dto.common.PaginationDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Product;
import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Review;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.SummaryDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.ReviewItemDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.request.ReviewCreateRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.request.ReviewUpdateRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.response.ReviewCreateResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.response.ReviewGetResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.response.ReviewUpdateResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.mapper.ReviewMapper;
import com.wanted_preonboarding_challenge_backend.eCommerce.respository.ProductRepository;
import com.wanted_preonboarding_challenge_backend.eCommerce.respository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    private final ReviewMapper reviewMapper;


    public ReviewGetResponse getReviews(Long id, int page, int perPage, String sort, Integer rating) {
        String[] sortParts = sort.split(":");
        String sortBy = sortParts[0];
        Sort.Direction direction = sortParts.length > 1 && "asc".equalsIgnoreCase(sortParts[1]) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(direction, sortBy));

        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException());

        Page<Review> reviewPage = reviewRepository.findByProductAndRating(product, pageable, rating);
        List<Review> allReviews = reviewRepository.findAllByProduct(product);

        List<ReviewItemDto> items = reviewMapper.toReviewItemDtoList(reviewPage.getContent());

        double averageRating = allReviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        Map<Integer, Long> distribution = allReviews.stream()
                .collect(Collectors.groupingBy(
                        Review::getRating,
                        Collectors.counting()
                ));

        SummaryDto summaryDto = SummaryDto.builder()
                .averageRating(averageRating)
                .totalCount(allReviews.size())
                .distribution(distribution).build();

        PaginationDto paginationDto = PaginationDto.builder()
                .totalItems(reviewPage.getTotalElements())
                .totalPages(reviewPage.getTotalPages())
                .currentPage(reviewPage.getNumber() + 1)
                .perPage(reviewPage.getSize())
                .build();

        ReviewGetResponse response =
                ReviewGetResponse.builder()
                        .items(items)
                        .summary(summaryDto)
                        .pagination(paginationDto)
                        .build();

        return response;
    }

    public void deleteReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException());

        reviewRepository.delete(review);
    }

    public ReviewCreateResponse writeReview(Long productId, ReviewCreateRequest request) {
        Long userId = 1L;

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException());

        Review review = reviewMapper.toEntity(request);

        review.writeReview(product);
        reviewRepository.save(review);

        ReviewCreateResponse response = reviewMapper.toReviewCreateResponse(review);
        return response;
    }

    public ReviewUpdateResponse updateReview(Long reviewId, ReviewUpdateRequest request) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException());

        review.updateReview(review.getRating(), review.getTitle(), review.getContent());

        ReviewUpdateResponse response = reviewMapper.toReviewUpdateResponse(review);

        return response;
    }
}
