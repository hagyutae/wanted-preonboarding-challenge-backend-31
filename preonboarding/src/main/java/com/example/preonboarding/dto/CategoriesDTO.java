package com.example.preonboarding.dto;

import com.example.preonboarding.domain.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriesDTO {
    private Long id;
    private String name;
    private String slug;
}
