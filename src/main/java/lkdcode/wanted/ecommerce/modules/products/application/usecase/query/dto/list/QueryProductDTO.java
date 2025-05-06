package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.list;

import com.fasterxml.jackson.annotation.JsonFormat;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.QueryProductBrandDTO;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.QueryProductSellerDTO;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record QueryProductDTO(
    String name,
    String slug,
    String shortDescription,
    BigDecimal basePrice,
    BigDecimal salePrice,
    String currency,
    QueryPrimaryImage primary_image,
    QueryProductBrandDTO brand,
    QueryProductSellerDTO seller,
    Double rating,
    int review_count,
    boolean in_stock,
    ProductStatus status,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    LocalDateTime created_at
) {
}