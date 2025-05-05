package com.dino.cqrs_challenge.presentation.model.rs;

import com.dino.cqrs_challenge.domain.entity.Product;
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

    public static CreateProductResponse create(Product product) {
        CreateProductResponse createProductResponse = new CreateProductResponse();
        createProductResponse.id = product.getId();
        createProductResponse.name = product.getName();
        createProductResponse.slug = product.getSlug();
        createProductResponse.createdAt = product.getCreatedAt();
        createProductResponse.updatedAt = product.getUpdatedAt();
        return createProductResponse;
    }
}
