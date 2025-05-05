package com.example.cqrsapp.review.dto.request;

import com.example.cqrsapp.product.domain.Product;
import com.example.cqrsapp.review.domain.Review;
import com.example.cqrsapp.user.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterReviewDto {

    private int rating;
    private String title;
    private String content;

    public Review toEntity(User user, Product product){
        return Review.builder()
                .user(user)
                .product(product)
                .title(this.getTitle())
                .content(this.getContent())
                .rating(this.getRating())
                .build();
    }
}
