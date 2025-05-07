package wanted.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.common.exception.CustomException;
import wanted.common.exception.code.GlobalExceptionCode;
import wanted.domain.category.dto.request.CategoryProductSearchCondition;
import wanted.domain.category.dto.response.CategoryProductResponse;
import wanted.domain.category.dto.response.CategoryResponse;
import wanted.domain.category.dto.response.CategoryTreeResponse;
import wanted.domain.category.entity.Category;
import wanted.domain.category.repository.CategoryRepository;
import wanted.domain.product.dto.response.Pagination;
import wanted.domain.product.dto.response.SimpleProductResponse;
import wanted.domain.product.entity.Product;
import wanted.domain.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<CategoryTreeResponse> getCategoryTree(Integer level) {
        int targetLevel = (level != null) ? level : 1;

        List<Category> categories = categoryRepository.findByLevel(targetLevel);
        return categories.stream()
                .map(CategoryTreeResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryProductResponse getCategoryProducts(Long categoryId, CategoryProductSearchCondition condition) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Category", categoryId)));
        CategoryResponse categoryResponse = CategoryResponse.of(category, null);

        int page = Optional.ofNullable(condition.page()).orElse(1) - 1;
        int perPage = Optional.ofNullable(condition.perPage()).orElse(10);
        Pageable pageable = PageRequest.of(page, perPage, parseSort(condition.sort()));

        List<Long> categoryIds;
        if (Boolean.TRUE.equals(condition.includeSubcategories())) {
            categoryIds = getAllCategoryIdsIncludingSub(category);
        } else {
            categoryIds = List.of(categoryId);
        }

        Page<Product> products = productRepository.findByProductCategories_Category_IdIn(categoryIds, pageable);
        List<SimpleProductResponse> items = products.getContent().stream()
                .map(SimpleProductResponse::from)
                .toList();

        return new CategoryProductResponse(categoryResponse, items,
                new Pagination(products.getTotalElements(), products.getTotalPages(), products.getNumber() + 1, products.getSize()));
    }

    private List<Long> getAllCategoryIdsIncludingSub(Category root) {
        List<Long> ids = new ArrayList<>();
        ids.add(root.getId());

        for (Category child : root.getChildren()) {
            ids.addAll(getAllCategoryIdsIncludingSub(child));
        }

        return ids;
    }


    public Sort parseSort(String sortString) {
        if (sortString == null || sortString.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        return Sort.by(
                Arrays.stream(sortString.split(","))
                        .map(s -> {
                            String[] parts = s.split(":");
                            String field = parts[0];
                            Sort.Direction direction = (parts.length > 1 && parts[1].equalsIgnoreCase("asc"))
                                    ? Sort.Direction.ASC : Sort.Direction.DESC;
                            return new Sort.Order(direction, field);
                        }).toList()
        );
    }


    private Map<String, Object> resourceNotFoundDetails(String type, Object id) {
        return Map.of("resourceType", type, "resourceId", id);
    }
}
