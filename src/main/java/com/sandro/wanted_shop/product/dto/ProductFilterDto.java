package com.sandro.wanted_shop.product.dto;

import java.time.LocalDate;

public record ProductFilterDto(
        String keyword,
        Long tagId,
        LocalDate startDate,
        LocalDate endDate,
        Long sellerId,
        Long brandId,
        Integer startPrice,
        Integer endPrice,
        Long categoryId,
        Boolean hasStock // 재고 유무
) {
}
