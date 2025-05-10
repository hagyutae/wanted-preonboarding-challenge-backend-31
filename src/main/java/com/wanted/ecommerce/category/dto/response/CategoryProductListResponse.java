package com.wanted.ecommerce.category.dto.response;

import com.wanted.ecommerce.common.dto.response.ProductItemResponse;
import com.wanted.ecommerce.common.response.Pagination;
import com.wanted.ecommerce.product.domain.Product;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record CategoryProductListResponse(
    CategoryResponse category,
    List<ProductItemResponse> item,
    Pagination pagination
) {

    public static CategoryProductListResponse of(CategoryResponse category, Page<Product> page) {
        return CategoryProductListResponse.builder()
            .category(category)
            .item(page.getContent().stream().map(ProductItemResponse::of).toList())
            .pagination(Pagination.builder()
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .perPage(page.getNumberOfElements())
                .build())
            .build();
    }
}
