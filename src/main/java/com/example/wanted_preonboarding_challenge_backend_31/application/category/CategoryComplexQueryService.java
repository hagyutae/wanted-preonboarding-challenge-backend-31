package com.example.wanted_preonboarding_challenge_backend_31.application.category;

import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.category.query.CategoryQueryRepository;
import com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.response.CategorySearchRes;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryComplexQueryService {

    private final CategoryQueryRepository categoryQueryRepository;

    public Map<Long, List<CategorySearchRes>> getChildCategoriesByParentId(int startLevel) {
        return categoryQueryRepository.groupAllCategoriesByParentId(startLevel);
    }

    public Map<Long, Long> getFeaturedCategoryMap(int limit) {
        return categoryQueryRepository.getFeaturedCategories(limit);
    }
}
