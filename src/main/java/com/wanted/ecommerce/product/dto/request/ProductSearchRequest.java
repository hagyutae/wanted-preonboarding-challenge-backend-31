package com.wanted.ecommerce.product.dto.request;

import jakarta.validation.constraints.Min;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductSearchRequest {
    private String status;
    @Min(value = 0, message = "최소 가격 입력값은 0 이상이어야 합니다.")
    private Integer minPrice;
    @Min(value = 0, message = "최대 가격 입력값은 0 이상이어야 합니다.")
    private Integer maxPrice;
    @Getter(AccessLevel.NONE)
    private String category;
    private Integer seller;
    private Integer brand;
    private Boolean inStock;
    private String search;

    public List<Integer> getCategory() {
        if (category == null || category.isBlank()) {
            return List.of();
        }
        return Arrays.stream(category.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(Integer::parseInt)
            .toList();
    }
}
