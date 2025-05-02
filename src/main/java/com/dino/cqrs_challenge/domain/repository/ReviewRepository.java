package com.dino.cqrs_challenge.domain.repository;

import com.dino.cqrs_challenge.domain.entity.Product;
import com.dino.cqrs_challenge.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProduct(Product product);

    List<Review> findAllByProductIdIn(List<Long> productIdList);
}
