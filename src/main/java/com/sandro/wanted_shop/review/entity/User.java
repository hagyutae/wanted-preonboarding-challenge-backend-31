package com.sandro.wanted_shop.review.entity;

import com.sandro.wanted_shop.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    private String avatarUrl;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @Builder
    public User(String name, String email, String avatarUrl, List<Review> reviews) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.reviews = Optional.ofNullable(reviews).orElse(new ArrayList<>());

        validate();
    }

    private void validate() {
        assert StringUtils.hasText(this.name)
                && StringUtils.hasText(this.email)
                : "Username and email are required";
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }
}