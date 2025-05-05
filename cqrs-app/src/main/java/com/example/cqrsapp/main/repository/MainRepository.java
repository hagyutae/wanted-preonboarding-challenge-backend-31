package com.example.cqrsapp.main.repository;


import com.example.cqrsapp.main.dto.CategoryDto;
import com.example.cqrsapp.main.dto.ProductDto;

import java.util.List;

public interface MainRepository {

    List<ProductDto> findNewProducts();

    List<ProductDto> findPopularProducts();

    List<CategoryDto> findFeaturedCategories();
}