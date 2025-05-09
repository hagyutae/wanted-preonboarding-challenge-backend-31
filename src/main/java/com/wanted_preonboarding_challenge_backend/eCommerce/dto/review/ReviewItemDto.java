package com.wanted_preonboarding_challenge_backend.eCommerce.dto.review;

import lombok.Data;

@Data
public
class ReviewItemDto {
    private Long id;
//    private UserDto user;
    private int rating;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private boolean verifiedPurchase;
    private int helpfulVotes;
}