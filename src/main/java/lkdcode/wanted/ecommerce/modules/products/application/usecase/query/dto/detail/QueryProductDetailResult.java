package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.QueryProductBrandDTO;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.QueryProductSellerDTO;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record QueryProductDetailResult(
    Long id,
    String name,
    String slug,
    String short_description,
    String full_description,
    QueryProductSellerDTO seller,
    QueryProductBrandDTO brand,
    ProductStatus status,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    LocalDateTime created_at,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    LocalDateTime updated_at,
    QueryProductDetailDTO detail,
    QueryProductPriceDTO price,
    List<QueryProductCategoryDTO> categories,
    List<QueryProductOptionGroup> option_groups,
    List<QueryProductImageDTO> images,
    List<QueryProductTagDTO> tags,
    QueryProductRatingDTO rating,
    List<QueryProductRelatedDTO> related_products
) {
}