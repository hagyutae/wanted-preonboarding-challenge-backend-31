package com.example.wanted_preonboarding_challenge_backend_31.application.category;

import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.category.query.CategoryQueryRepository;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category.CategoryDetailDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryComplexQueryService {

    private final CategoryQueryRepository categoryQueryRepository;

    public List<CategoryDetailDto> getCategoryDetailsByProductId(Long productId) {
        return categoryQueryRepository.getCategoryDetailsByProductId(productId);
    }
}
