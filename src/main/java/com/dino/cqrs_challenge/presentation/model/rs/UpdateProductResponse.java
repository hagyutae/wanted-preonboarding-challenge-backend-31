package com.dino.cqrs_challenge.presentation.model.rs;

import com.dino.cqrs_challenge.domain.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "상품 수정 응답 DTO")
public class UpdateProductResponse {

    @Schema(description = "상품 식별번호")
    private Long id;

    @Schema(description = "상품명")
    private String name;

    @Schema(description = "슬러그")
    private String slug;

    @Schema(description = "수정 시각")
    private LocalDateTime updatedAt;

    public static UpdateProductResponse create(Product product) {
        UpdateProductResponse updateProductResponse = new UpdateProductResponse();
        updateProductResponse.id = product.getId();
        updateProductResponse.name = product.getName();
        updateProductResponse.slug = product.getSlug();
        updateProductResponse.updatedAt = product.getUpdatedAt();
        return updateProductResponse;
    }
}
