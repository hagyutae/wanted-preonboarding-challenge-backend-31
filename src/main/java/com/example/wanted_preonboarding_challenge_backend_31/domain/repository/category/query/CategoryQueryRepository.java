package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.category.query;

import com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.response.CategorySearchRes;
import java.util.List;
import java.util.Map;

public interface CategoryQueryRepository {

    Map<Long, List<CategorySearchRes>> groupAllCategoriesByParentId(int startLevel);

    Map<Long, Long> getFeaturedCategories(int limit);
}
