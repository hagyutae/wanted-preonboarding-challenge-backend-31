package minseok.cqrschallenge.main.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import minseok.cqrschallenge.category.entity.Category;
import minseok.cqrschallenge.category.repository.CategoryRepository;
import minseok.cqrschallenge.main.dto.response.FeaturedCategoryResponse;
import minseok.cqrschallenge.main.dto.response.MainPageResponse;
import minseok.cqrschallenge.product.dto.response.ProductListResponse;
import minseok.cqrschallenge.product.dto.response.ProductListResponse.BrandSummary;
import minseok.cqrschallenge.product.dto.response.ProductListResponse.ProductImageSummary;
import minseok.cqrschallenge.product.dto.response.ProductListResponse.SellerSummary;
import minseok.cqrschallenge.product.entity.Product;
import minseok.cqrschallenge.product.entity.ProductImage;
import minseok.cqrschallenge.product.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private static final int NEW_PRODUCTS_LIMIT = 10;

    private static final int POPULAR_PRODUCTS_LIMIT = 10;

    private static final int FEATURED_CATEGORIES_LIMIT = 5;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public MainPageResponse getMainPageData() {
        List<ProductListResponse> newProducts = getNewProducts();
        List<ProductListResponse> popularProducts = getPopularProducts();
        List<FeaturedCategoryResponse> featuredCategories = getFeaturedCategories();

        return MainPageResponse.builder()
            .newProducts(newProducts)
            .popularProducts(popularProducts)
            .featuredCategories(featuredCategories)
            .build();
    }

    private List<ProductListResponse> getNewProducts() {
        PageRequest pageRequest = PageRequest.of(0, NEW_PRODUCTS_LIMIT,
            Sort.by(Sort.Direction.DESC, "createdAt"));

        List<Product> products = productRepository.findNewProducts(pageRequest);

        return products.stream()
            .map(this::convertToProductListResponse)
            .collect(Collectors.toList());
    }

    private List<ProductListResponse> getPopularProducts() {
        PageRequest pageRequest = PageRequest.of(0, POPULAR_PRODUCTS_LIMIT);

        List<Product> products = productRepository.findPopularProducts(pageRequest);

        return products.stream()
            .map(this::convertToProductListResponse)
            .collect(Collectors.toList());
    }

    private List<FeaturedCategoryResponse> getFeaturedCategories() {
        PageRequest pageRequest = PageRequest.of(0, FEATURED_CATEGORIES_LIMIT);

        List<Category> categories = categoryRepository.findFeaturedCategories(pageRequest);

        return categories.stream()
            .map(this::convertToFeaturedCategoryResponse)
            .collect(Collectors.toList());
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
        return product.getReviews().stream()
            .mapToDouble(review -> review.getRating() != null ? review.getRating() : 0)
            .average()
            .orElse(0.0);
    }

    private Integer getReviewCount(Product product) {
        return product.getReviews().size();
    }

    private boolean isInStock(Product product) {
        return product.getOptionGroups().stream()
            .flatMap(group -> group.getOptions().stream())
            .anyMatch(option -> option.getStock() > 0);
    }

    private FeaturedCategoryResponse convertToFeaturedCategoryResponse(Category category) {
        return FeaturedCategoryResponse.builder()
            .id(category.getId())
            .name(category.getName())
            .slug(category.getSlug())
            .imageUrl(category.getImageUrl())
            .productCount(getProductCount(category))
            .build();
    }

    private Integer getProductCount(Category category) {
        return category.getProducts().size();
    }
}