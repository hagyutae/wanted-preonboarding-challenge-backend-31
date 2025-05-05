package com.example.cqrsapp.main.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MainResponse {
    private List<ProductDto> newProducts;
    private List<ProductDto> popularProducts;
    private List<CategoryDto> featuredCategories;
}
