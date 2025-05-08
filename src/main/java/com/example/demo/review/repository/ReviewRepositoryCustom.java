package com.example.demo.review.repository;

import com.example.demo.review.dto.ReviewDistribution;
import com.example.demo.review.dto.ReviewQueryFilter;
import com.example.demo.review.dto.ReviewStatistic;
import com.example.demo.review.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface ReviewRepositoryCustom {
    List<ReviewStatistic> findAllByProductIds(Collection<Long> productIds);
    ReviewDistribution findDistributionByProductId(Long productId);
    List<Long> findHotTop10ProductIds();
    Page<ReviewEntity> findPage(ReviewQueryFilter reviewQueryFilter, Pageable pageable);
}
