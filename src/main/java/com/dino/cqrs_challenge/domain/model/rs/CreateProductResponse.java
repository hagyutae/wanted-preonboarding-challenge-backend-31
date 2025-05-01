package com.dino.cqrs_challenge.domain.model.rs;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "상품 생성 응답 DTO")
public class CreateProductResponse {

    @Schema(description = "상품 식별번호")
    private Long id;

    @Schema(description = "상품명")
    private String name;

    @Schema(description = "슬러그")
    private String slug;

    @Schema(description = "생성 시각")
    private LocalDateTime createdAt;

    @Schema(description = "수정 시각")
    private LocalDateTime updatedAt;
}
