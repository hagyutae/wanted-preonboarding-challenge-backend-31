package com.wanted.ecommerce.product.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductSearchRequest {
    @Builder.Default
    @Min(value = 1, message = "페이지 입력값은 1 이상이어야 합니다.")
    private Integer page = 1;
    @Builder.Default
    @Min(value = 10, message = "페이지 번호 입력값은 10 이상이어야 합니다.")
    private Integer perPage = 10;
    @Builder.Default
    @Pattern(regexp = "^[a-zA-Z_]+(:(asc|desc))?$", message = "올바른 포맷이 아닙니다.")
    private String sort = "created_at:desc";
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

    public Map<String, String> getSort(){
        String[] sorts = sort.split(",");
        return Arrays.stream(sorts)
            .map(sort1 -> {
                String[] sortKeyValue = sort1.split(":");
                String key = sortKeyValue[0];
                String value = sortKeyValue[1];
                return new AbstractMap.SimpleEntry<>(key, value);
            })
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
