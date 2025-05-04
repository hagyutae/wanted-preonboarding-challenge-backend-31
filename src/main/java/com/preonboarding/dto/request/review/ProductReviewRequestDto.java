package com.preonboarding.dto.request.review;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReviewRequestDto {
    private Integer rating;
    private String title;
    private String content;
}
