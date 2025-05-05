package com.example.cqrsapp.review.dto.response;

import com.example.cqrsapp.common.dto.ReviewRatingDto;
import com.example.cqrsapp.common.response.PaginationDto;
import com.example.cqrsapp.user.domain.User;
import com.example.cqrsapp.review.domain.Review;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.cqrsapp.common.dto.ReviewRatingDto.DistributionDto;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewListResponse {
    private List<ReviewItemDto> item;
    private ReviewRatingDto summary;
    private PaginationDto pagination;
    public ReviewListResponse(Page<ReviewItemDto> page, ReviewRatingDto summary) {
        this.item = page.getContent();
        this.summary = summary;
        this.pagination = PaginationDto.builder()
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .perPage(page.getNumberOfElements())
                .build();
    }

    public static ReviewListResponse of(Page<ReviewItemDto> item, ReviewRatingDto ratingDto, List<DistributionDto> distribution) {
        Map<String, Long> distributionMap = distribution.stream().collect(Collectors.toMap(DistributionDto::getRating, DistributionDto::getCount));
        ratingDto.setDistribution(distributionMap);
        return new ReviewListResponse(item, ratingDto);
    }
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ReviewItemDto {
        private Long id;
        private UserDto user;
        private int rating;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Boolean verifiedPurchase;
        private Integer helpfulVotes;

        public static ReviewItemDto fromEntity(Review review) {
            return ReviewItemDto.builder()
                    .id(review.getId())
                    .user(UserDto.of(review.getUser()))
                    .rating(review.getRating())
                    .title(review.getTitle())
                    .content(review.getContent())
                    .createdAt(review.getCreatedAt())
                    .updatedAt(review.getUpdatedAt())
                    .verifiedPurchase(review.getVerifiedPurchase())
                    .helpfulVotes(review.getHelpfulVotes())
                    .build();
        }
    }

    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UserDto {
        private Long id;
        private String name;
        private String avatarUrl;

        public static UserDto of(User user) {
            return UserDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .avatarUrl(user.getAvatarUrl())
                    .build();
        }
    }
}
