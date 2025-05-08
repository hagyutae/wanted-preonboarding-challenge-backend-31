package com.example.demo.category.dto;

import lombok.Builder;

import java.util.Collection;

@Builder
public record CategoryQueryFilter(
        Long categoryId,
        Collection<Long> categoryIds,
        Long parentId,
        Integer level
) {
}
