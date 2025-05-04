package com.preonboarding.dto.request.product;

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
