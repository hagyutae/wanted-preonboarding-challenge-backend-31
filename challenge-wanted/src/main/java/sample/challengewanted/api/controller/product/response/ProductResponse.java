package sample.challengewanted.api.controller.product.response;

import lombok.Builder;
import lombok.Getter;
import sample.challengewanted.domain.product.entity.Product;

import java.time.LocalDateTime;

@Getter
public class ProductResponse {

    private Long id;
    private String name;
    private String slug;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private ProductResponse(Long id, String name, String description, String slug, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

}
