package minseok.cqrschallenge.category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import minseok.cqrschallenge.category.dto.response.CategoryDetailResponse;
import minseok.cqrschallenge.category.dto.response.CategoryParentResponse;
import minseok.cqrschallenge.category.dto.response.CategoryProductsResponse;
import minseok.cqrschallenge.category.dto.response.CategoryResponse;
import minseok.cqrschallenge.category.entity.Category;
import minseok.cqrschallenge.category.repository.CategoryRepository;
import minseok.cqrschallenge.common.dto.PaginationResponse;
import minseok.cqrschallenge.common.exception.ResourceNotFoundException;
import minseok.cqrschallenge.product.dto.response.ProductListResponse;
import minseok.cqrschallenge.product.dto.response.ProductListResponse.BrandSummary;
import minseok.cqrschallenge.product.dto.response.ProductListResponse.ProductImageSummary;
import minseok.cqrschallenge.product.dto.response.ProductListResponse.SellerSummary;
import minseok.cqrschallenge.product.entity.Product;
import minseok.cqrschallenge.product.entity.ProductImage;
import minseok.cqrschallenge.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategories(Integer level) {
        if (level != null) {
            List<Category> categories = categoryRepository.findByLevel(level);
            return categories.stream()
                    .map(this::convertToCategoryResponse)
                    .collect(Collectors.toList());
        } else {
            List<Category> rootCategories = categoryRepository.findAllRootCategories();
            return rootCategories.stream()
                    .map(this::convertToCategoryWithChildrenResponse)
                    .collect(Collectors.toList());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public CategoryProductsResponse getCategoryProducts(Long categoryId, int page, int perPage, String sort, boolean includeSubcategories) {
        Category category = categoryRepository.findByIdWithParent(categoryId);
        
        if (category == null) {
            throw new ResourceNotFoundException("요청한 카테고리를 찾을 수 없습니다.");
        }
        
        String[] sortParts = sort.split(":");
        String sortField = convertSortField(sortParts[0]);
        Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("asc") 
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        
        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(direction, sortField));
        
        List<Long> categoryIds = new ArrayList<>();
        categoryIds.add(categoryId);
        
        if (includeSubcategories) {
            addSubcategoryIds(category, categoryIds);
        }
        
        Page<Product> productPage = productRepository.findByCategoryIds(categoryIds, pageable);
        
        List<ProductListResponse> productResponses = productPage.getContent().stream()
                .map(this::convertToProductListResponse)
                .collect(Collectors.toList());
        
        CategoryDetailResponse categoryDetail = convertToCategoryDetailResponse(category);
        
        PaginationResponse.Pagination pagination = PaginationResponse.Pagination.builder()
                .totalItems(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .currentPage(page)
                .perPage(perPage)
                .build();
        
        return CategoryProductsResponse.builder()
                .category(categoryDetail)
                .items(productResponses)
                .pagination(pagination)
                .build();
    }
    
    private void addSubcategoryIds(Category category, List<Long> categoryIds) {
        for (Category child : category.getChildren()) {
            categoryIds.add(child.getId());
            addSubcategoryIds(child, categoryIds);
        }
    }
    
    private CategoryResponse convertToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .level(category.getLevel())
                .imageUrl(category.getImageUrl())
                .build();
    }
    
    private CategoryResponse convertToCategoryWithChildrenResponse(Category category) {
        List<CategoryResponse> childrenResponses = category.getChildren().stream()
                .map(this::convertToCategoryWithChildrenResponse)
                .collect(Collectors.toList());
        
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .level(category.getLevel())
                .imageUrl(category.getImageUrl())
                .children(childrenResponses)
                .build();
    }
    
    private CategoryDetailResponse convertToCategoryDetailResponse(Category category) {
        CategoryParentResponse parentResponse = null;
        
        if (category.getParent() != null) {
            parentResponse = CategoryParentResponse.builder()
                    .id(category.getParent().getId())
                    .name(category.getParent().getName())
                    .slug(category.getParent().getSlug())
                    .build();
        }
        
        return CategoryDetailResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .level(category.getLevel())
                .imageUrl(category.getImageUrl())
                .parent(parentResponse)
                .build();
    }
    
    private ProductListResponse convertToProductListResponse(Product product) {
        return ProductListResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .basePrice(product.getPrice() != null ? product.getPrice().getBasePrice() : null)
                .salePrice(product.getPrice() != null ? product.getPrice().getSalePrice() : null)
                .currency(product.getPrice() != null ? product.getPrice().getCurrency() : null)
                .primaryImage(getPrimaryImage(product))
                .brand(getBrandResponse(product))
                .seller(getSellerResponse(product))
                .rating(calculateAverageRating(product))
                .reviewCount(getReviewCount(product))
                .inStock(isInStock(product))
                .status(product.getStatus().name())
                .createdAt(product.getCreatedAt())
                .build();
    }
    
    private ProductImageSummary getPrimaryImage(Product product) {
        return product.getImages().stream()
                .filter(ProductImage::getIsPrimary)
                .findFirst()
                .map(image -> ProductImageSummary.builder()
                        .url(image.getUrl())
                        .altText(image.getAltText())
                        .build())
                .orElse(null);
    }
    
    private BrandSummary getBrandResponse(Product product) {
        return product.getBrand() != null
                ? BrandSummary.builder()
                        .id(product.getBrand().getId())
                        .name(product.getBrand().getName())
                        .build()
                : null;
    }
    
    private SellerSummary getSellerResponse(Product product) {
        return product.getSeller() != null
                ? SellerSummary.builder()
                        .id(product.getSeller().getId())
                        .name(product.getSeller().getName())
                        .build()
                : null;
    }
    
    private Double calculateAverageRating(Product product) {
        return 0.0; // 임시
    }
    
    private Integer getReviewCount(Product product) {
        return 0; // 임시
    }
    
    private boolean isInStock(Product product) {
        return product.getOptionGroups().stream()
                .flatMap(group -> group.getOptions().stream())
                .anyMatch(option -> option.getStock() > 0);
    }
    
    private String convertSortField(String fieldName) {
        return switch (fieldName) {
            case "created_at" -> "createdAt";
            case "updated_at" -> "updatedAt";
            case "base_price" -> "price.basePrice";
            case "sale_price" -> "price.salePrice";
            default -> fieldName;
        };
    }
}