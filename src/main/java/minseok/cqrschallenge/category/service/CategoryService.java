package minseok.cqrschallenge.category.service;

import java.util.List;
import minseok.cqrschallenge.category.dto.response.CategoryProductsResponse;
import minseok.cqrschallenge.category.dto.response.CategoryResponse;

public interface CategoryService {

    List<CategoryResponse> getCategories(Integer level);

    CategoryProductsResponse getCategoryProducts(Long categoryId, int page, int perPage,
        String sort, boolean includeSubcategories);
}