package kr.co.wanted.backend31.controller.vo;

import java.time.LocalDateTime;

import kr.co.wanted.backend31.common.model.product.Product;

public record ProductCreateResponse(Long id, String name, String slug, LocalDateTime createdAt,
                LocalDateTime updatedAt) {

        public static ProductCreateResponse of(Product product) {
                return new ProductCreateResponse(product.getId(), product.getName(), product.getSlug(),
                                product.getCreatedAt(), product.getUpdatedAt());
        }

}
