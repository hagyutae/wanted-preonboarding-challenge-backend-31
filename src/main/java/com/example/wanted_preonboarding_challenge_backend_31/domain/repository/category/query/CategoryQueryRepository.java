package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.category.query;

import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category.CategoryDetailDto;
import java.util.List;

public interface CategoryQueryRepository {

    List<CategoryDetailDto> getCategoryDetailsByProductId(Long productId);
}
