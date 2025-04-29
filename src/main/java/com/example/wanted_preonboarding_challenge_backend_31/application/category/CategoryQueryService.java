package com.example.wanted_preonboarding_challenge_backend_31.application.category;

import com.example.wanted_preonboarding_challenge_backend_31.common.dto.ErrorInfo;
import com.example.wanted_preonboarding_challenge_backend_31.common.exception.CommonErrorType;
import com.example.wanted_preonboarding_challenge_backend_31.common.exception.CustomException;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.category.Category;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.category.CategoryRepository;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category.CategoryDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.response.CategorySearchRes;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        ErrorInfo.of(CommonErrorType.RESOURCE_NOT_FOUND, "요청한 카테고리를 찾을 수 없습니다, ID:" + id)));
    }

    public CategoryDetailDto getCategoryDetailById(Long id) {
        Category category = getById(id);
        return CategoryDetailDto.from(category);
    }

    public List<CategorySearchRes> findAllByLevel(int level) {
        return categoryRepository.findAllByLevel(level)
                .stream()
                .map(category -> new CategorySearchRes(category.getId(), category.getName(), category.getSlug(),
                        category.getDescription(), category.getLevel(), category.getImageUrl()))
                .toList();
    }

    /**
     * includeSubcategories에 따라 하위 카테고리를 포함하거나 포함하지 않고 Id 리스트를 반환한다.<br> 부모 카테고리 Id는 반드시 포함된다.
     *
     * @param categoryId           부모 카테고리 Id
     * @param includeSubcategories 하위 카테고리 포함 여부 - 기본값 : TRUE
     * @return 카테고리 Id 리스트
     */
    public List<Long> getSubcategoryIds(Long categoryId, Boolean includeSubcategories) {
        if (Boolean.FALSE.equals(includeSubcategories)) {
            return List.of(categoryId);
        }
        return categoryRepository.findAllChildCategoryIds(categoryId);
    }

    public List<Category> findAllByIds(Set<Long> ids) {
        return categoryRepository.findAllById(ids);
    }
}
