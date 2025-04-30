package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.config.GenericMapper;
import com.mkhwang.wantedcqrs.config.exception.ForbiddenException;
import com.mkhwang.wantedcqrs.config.exception.ResourceNotFoundException;
import com.mkhwang.wantedcqrs.product.domain.Product;
import com.mkhwang.wantedcqrs.product.domain.Review;
import com.mkhwang.wantedcqrs.product.domain.dto.review.ReviewCreateDto;
import com.mkhwang.wantedcqrs.product.domain.dto.review.ReviewModifyDto;
import com.mkhwang.wantedcqrs.product.domain.dto.review.ReviewResponseDto;
import com.mkhwang.wantedcqrs.product.domain.dto.review.ReviewSearchDto;
import com.mkhwang.wantedcqrs.product.infra.ProductRepository;
import com.mkhwang.wantedcqrs.product.infra.ReviewRepository;
import com.mkhwang.wantedcqrs.product.infra.ReviewSearchRepository;
import com.mkhwang.wantedcqrs.user.domain.User;
import com.mkhwang.wantedcqrs.user.infra.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
  @Mock
  ReviewRepository reviewRepository;
  @Mock
  ReviewSearchRepository reviewSearchRepository;
  @Mock
  ProductRepository productRepository;
  @Mock
  UserRepository userRepository;
  @Mock
  GenericMapper genericMapper;
  @InjectMocks
  ReviewService reviewService;

  @Test
  void test_getProductReviews() {
    // given
    Long productId = 1L;
    ReviewSearchDto searchDto = mock(ReviewSearchDto.class);

    // when
    reviewService.getProductReviews(productId, searchDto);

    // then
    verify(reviewSearchRepository).findRatingByProductId(productId, searchDto);
  }

  @DisplayName("postReview() 메서드 테스트")
  @Test
  void test_postReview() {
    // given
    ReviewCreateDto reviewDto = new ReviewCreateDto();
    reviewDto.setUserId(1L);
    reviewDto.setProductId(1L);
    User user = mock(User.class);
    Product product = mock(Product.class);
    Review review = mock(Review.class);

    given(userRepository.findById(reviewDto.getUserId())).willReturn(Optional.of(user));
    given(productRepository.findById(reviewDto.getProductId())).willReturn(Optional.of(product));
    given(reviewRepository.save(any(Review.class))).willReturn(review);

    // when
    reviewService.postReview(reviewDto);

    // then
    verify(reviewRepository).save(argThat(savedReview ->
            savedReview.getUser().equals(user) &&
                    savedReview.getProduct().equals(product)
    ));
  }

  @DisplayName("postReview() 메서드 테스트 - 리뷰 저장 안됨")
  @Test
  void test_postReview_neverSaved() {
    // given
    ReviewCreateDto reviewDto = new ReviewCreateDto();
    reviewDto.setUserId(1L);
    reviewDto.setProductId(1L);
    Review review = mock(Review.class);

    given(userRepository.findById(reviewDto.getUserId())).willReturn(Optional.empty());

    // when
    assertThrows(ResourceNotFoundException.class, () -> {
      reviewService.postReview(reviewDto);
    });

    // then
    verify(reviewRepository, never()).save(review);
  }

  @DisplayName("deleteReview() 메서드 테스트")
  @Test
  void test_deleteReview() {
    Long id = 1L;
    Long userId = 1L;

    Review existReview = mock(Review.class);
    given(reviewRepository.findById(id)).willReturn(Optional.of(existReview));

    when(existReview.getUser()).thenReturn(mock(User.class));
    when(existReview.getUser().getId()).thenReturn(userId);

    // when
    reviewService.deleteReview(id, userId);

    // then
    verify(reviewRepository).deleteById(id);
  }

  @DisplayName("deleteReview() 메서드 테스트 - 리뷰 삭제 권한 없음")
  @Test
  void test_deleteReview_noAuthority() {
    // given
    Long id = 1L;
    Long userId = 1L;

    Review existReview = mock(Review.class);
    given(reviewRepository.findById(id)).willReturn(Optional.of(existReview));

    when(existReview.getUser()).thenReturn(mock(User.class));
    when(existReview.getUser().getId()).thenReturn(2L);

    // when & then
    assertThrows(ForbiddenException.class, () -> {
      reviewService.deleteReview(id, userId);
    });
  }

  @DisplayName("updateReview() 메서드 테스트")
  @Test
  void test_updateReview() {
    // given
    ReviewModifyDto reviewDto = new ReviewModifyDto();
    reviewDto.setId(1L);
    reviewDto.setUserId(1L);
    reviewDto.setTitle("Updated Title");
    reviewDto.setContent("Updated Content");
    reviewDto.setRating(5);

    Review existReview = mock(Review.class);
    given(reviewRepository.findById(reviewDto.getId())).willReturn(Optional.of(existReview));

    when(existReview.getUser()).thenReturn(mock(User.class));
    when(existReview.getUser().getId()).thenReturn(reviewDto.getUserId());
    when(existReview.getTitle()).thenReturn(reviewDto.getTitle());
    when(existReview.getContent()).thenReturn(reviewDto.getContent());
    when(existReview.getRating()).thenReturn(reviewDto.getRating());

    when(genericMapper.toDto(any(Review.class), eq(ReviewResponseDto.class))).thenReturn(mock(ReviewResponseDto.class));

    when(reviewRepository.save(any(Review.class))).thenReturn(existReview);

    // when
    reviewService.updateReview(reviewDto);

    // then
    verify(reviewRepository).save(argThat(updatedReview ->
            updatedReview.getTitle().equals(reviewDto.getTitle()) &&
                    updatedReview.getContent().equals(reviewDto.getContent()) &&
                    updatedReview.getRating() == reviewDto.getRating()
    ));


  }
}