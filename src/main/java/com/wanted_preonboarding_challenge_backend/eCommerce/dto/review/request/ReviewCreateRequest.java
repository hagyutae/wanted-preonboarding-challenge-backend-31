package com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateRequest {

    private int rating;

    private String title;

    private String content;
}
