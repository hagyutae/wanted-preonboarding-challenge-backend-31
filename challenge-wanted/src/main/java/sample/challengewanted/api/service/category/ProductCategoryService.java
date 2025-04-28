package sample.challengewanted.api.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.challengewanted.api.controller.category.request.CategoryRequest;
import sample.challengewanted.api.controller.product.request.ProductCreateRequest;
import sample.challengewanted.domain.category.Category;
import sample.challengewanted.domain.category.ProductCategory;
import sample.challengewanted.domain.category.ProductCategoryRepository;
import sample.challengewanted.domain.product.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public void saveProductCategories(ProductCreateRequest request, List<Category> categories, Product product) {
        List<ProductCategory> productCategories = mapCategoryListToProductCategoryList(request, categories, product);
        productCategoryRepository.saveAll(productCategories);
    }

    private List<ProductCategory> mapCategoryListToProductCategoryList(ProductCreateRequest request, List<Category> categories, Product product) {
        return categories.stream()
                .map(category -> createProductCategory(request, product, category))
                .collect(Collectors.toList());
    }

    private ProductCategory createProductCategory(ProductCreateRequest request, Product product, Category category) {
        boolean isPrimary = isPrimaryCategory(request, category.getId());
        return ProductCategory.create(product, category, isPrimary);
    }

    private boolean isPrimaryCategory(ProductCreateRequest request, Long categoryId) {
        return request.getCategories().stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .map(CategoryRequest::isPrimary)
                .orElse(false);
    }

}
