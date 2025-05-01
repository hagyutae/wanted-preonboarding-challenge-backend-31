package com.dino.cqrs_challenge.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Schema(description = "상품 평점 상세 정보 DTO")
public class ProductRatingDTO {

    @Schema(description = "상품 평점 평균값")
    private BigDecimal average;

    @Schema(description = "상품 평점 개수")
    private Long count;

    @Schema(description = "평점 당 갯수")
    private Map<Integer, Integer> distribution = new HashMap<>();

}
