package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Schema(description = "상품 평점 상세 정보 DTO")
public class ProductRatingDTO {

    @Schema(description = "상품 평점 평균값")
    private Double average;

    @Schema(description = "상품 평점 개수")
    private Integer count;

    @Schema(description = "평점 당 갯수")
    private Map<Integer, Integer> distribution = new HashMap<>();

    public static ProductRatingDTO from(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) return null;

        ProductRatingDTO dto = new ProductRatingDTO();
        int sum = reviews.stream().mapToInt(Review::getRating).sum();
        dto.count = reviews.size();
        dto.average = Math.round((sum / (double) dto.count) * 10) / 10.0;
        dto.distribution = reviews.stream().collect(Collectors.groupingBy(
                Review::getRating,
                Collectors.reducing(0, e -> 1, Integer::sum)
        ));
        return dto;
    }
}
