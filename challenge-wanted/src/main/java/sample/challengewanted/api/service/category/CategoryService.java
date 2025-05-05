package sample.challengewanted.api.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.challengewanted.api.controller.category.request.CategoryRequest;
import sample.challengewanted.api.controller.product.request.ProductCreateRequest;
import sample.challengewanted.domain.category.Category;
import sample.challengewanted.domain.category.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findByIdIn(ProductCreateRequest request) {
        List<Long> categoryIds = request.getCategories().stream()
                .map(CategoryRequest::getCategoryId)
                .toList();

        return categoryRepository.findByIdIn(categoryIds);
    }
}
