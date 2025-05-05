package com.example.cqrsapp.review.dto.response;


import com.example.cqrsapp.review.domain.Review;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateReviewResponseDto {
    Long id;
    Integer rating;
    String title;
    String content;
    LocalDateTime updatedAt;

    public static UpdateReviewResponseDto fromEntity(Review review){
        return UpdateReviewResponseDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
