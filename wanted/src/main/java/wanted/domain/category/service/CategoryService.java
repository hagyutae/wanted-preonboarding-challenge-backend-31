package wanted.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.domain.category.dto.response.CategoryTreeResponse;
import wanted.domain.category.entity.Category;
import wanted.domain.category.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryTreeResponse> getCategoryTree(Integer level) {
        int targetLevel = (level != null) ? level : 1;

        List<Category> categories = categoryRepository.findByLevel(targetLevel);
        return categories.stream()
                .map(CategoryTreeResponse::of)
                .toList();
    }
}
