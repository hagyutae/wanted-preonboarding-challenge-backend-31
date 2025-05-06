package minseok.cqrschallenge.product.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import minseok.cqrschallenge.common.dto.PaginationResponse;
import minseok.cqrschallenge.common.exception.ConflictException;
import minseok.cqrschallenge.common.exception.ResourceNotFoundException;
import minseok.cqrschallenge.product.dto.request.ProductCreateRequest;
import minseok.cqrschallenge.product.dto.request.ProductUpdateRequest;
import minseok.cqrschallenge.product.dto.response.ProductCreateResponse;
import minseok.cqrschallenge.product.dto.response.ProductDetailResponse;
import minseok.cqrschallenge.product.dto.response.ProductListResponse;
import minseok.cqrschallenge.product.dto.response.ProductUpdateResponse;
import minseok.cqrschallenge.product.entity.Product;
import minseok.cqrschallenge.product.entity.ProductStatus;
import minseok.cqrschallenge.product.repository.ProductRepository;
import minseok.cqrschallenge.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductCreateResponse createProduct(ProductCreateRequest request) {
        Product product = convertToEntity(request);

        if (productRepository.existsBySlug(request.getSlug())) {
            throw new ConflictException("해당 슬러그는 이미 사용 중입니다.", "slug", request.getSlug());
        }
        Product savedProduct = productRepository.save(product);
        return convertToCreateResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<ProductListResponse> getProducts(
            int page, int perPage, String sort, String status,
            Integer minPrice, Integer maxPrice, String category,
            Integer seller, Integer brand, Boolean inStock, String search) {
        
        Pageable pageable = createPageable(page, perPage, sort);
        
        Page<Product> productPage = productRepository.findWithFilters(
                status, minPrice, maxPrice, category, seller, brand, inStock, search, pageable);
        
        List<ProductListResponse> productResponses = productPage.getContent().stream()
                .map(this::convertToListResponse)
                .collect(Collectors.toList());
        
        return PaginationResponse.<ProductListResponse>builder()
                .items(productResponses)
                .pagination(PaginationResponse.Pagination.builder()
                        .totalItems(productPage.getTotalElements())
                        .totalPages(productPage.getTotalPages())
                        .currentPage(page)
                        .perPage(perPage)
                        .build())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailResponse getProductDetail(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다."));
        
        return convertToDetailResponse(product);
    }

    @Override
    @Transactional
    public ProductUpdateResponse updateProduct(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다."));

        product.update(
                request.getName(),
                request.getSlug(),
                request.getShortDescription(),
                request.getFullDescription(),
                ProductStatus.valueOf(request.getStatus())
        );
        return convertToUpdateResponse(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다."));
        if (product.isDeleted()) {
            throw new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다.");
        }
       product.delete();
    }

    private Pageable createPageable(int page, int perPage, String sort) {
        String[] sortParts = sort.split(":");
        String sortField = convertSortField(sortParts[0]);
        Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("asc")
            ? Sort.Direction.ASC : Sort.Direction.DESC;

        return PageRequest.of(page - 1, perPage, Sort.by(direction, sortField));
    }

    private String convertSortField(String fieldName) {
        return switch (fieldName) {
            case "created_at" -> "createdAt";
            case "updated_at" -> "updatedAt";
            default -> fieldName;
        };
    }
    private Product convertToEntity(ProductCreateRequest request) {
        return Product.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .shortDescription(request.getShortDescription())
                .fullDescription(request.getFullDescription())
                .status(ProductStatus.valueOf(request.getStatus()))
                .build();
    }
    

    private ProductListResponse convertToListResponse(Product product) {
        return ProductListResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .basePrice(product.getPrice().getBasePrice())
                .salePrice(product.getPrice().getSalePrice())
                .currency(product.getPrice().getCurrency())
                .status(product.getStatus().name())
                .createdAt(product.getCreatedAt())
                .build();
    }

    private ProductDetailResponse convertToDetailResponse(Product product) {
        ProductDetailResponse.SellerDetail sellerDetail = null;
        if (product.getSeller() != null) {
            sellerDetail = ProductDetailResponse.SellerDetail.builder()
                .id(product.getSeller().getId())
                .name(product.getSeller().getName())
                .description(product.getSeller().getDescription())
                .logoUrl(product.getSeller().getLogoUrl())
                .rating(product.getSeller().getRating())
                .contactEmail(product.getSeller().getContactEmail())
                .contactPhone(product.getSeller().getContactPhone())
                .build();
        }

        ProductDetailResponse.BrandDetail brandDetail = null;
        if (product.getBrand() != null) {
            brandDetail = ProductDetailResponse.BrandDetail.builder()
                .id(product.getBrand().getId())
                .name(product.getBrand().getName())
                .description(product.getBrand().getDescription())
                .logoUrl(product.getBrand().getLogoUrl())
                .website(product.getBrand().getWebsite())
                .build();
        }
        ProductDetailResponse.ProductPriceInfo productPriceInfo = null;
        if (product.getPrice() != null) {
            productPriceInfo = ProductDetailResponse.ProductPriceInfo.builder()
                .basePrice(product.getPrice().getBasePrice())
                .salePrice(product.getPrice().getSalePrice())
                .currency(product.getPrice().getCurrency())
                .taxRate(product.getPrice().getTaxRate())
                .discountPercentage(
                    product.getPrice().getBasePrice()
                        .subtract(product.getPrice().getSalePrice())
                        .multiply(new BigDecimal("100"))
                        .divide(product.getPrice().getBasePrice(), 0, RoundingMode.HALF_UP)
                        .intValue()
                ).build();
        }

            List<ProductDetailResponse.CategoryInfo> categoryInfoList = null;
            if (product.getCategories() != null && !product.getCategories().isEmpty()) {
                categoryInfoList = product.getCategories().stream()
                    .map(category -> ProductDetailResponse.CategoryInfo.builder()
                        .id(category.getId())
                        .name(category.getCategory().getName())
                        .slug(category.getCategory().getSlug())
                        .isPrimary(category.getIsPrimary())
                        .parent(category.getCategory().getParent() != null
                            ? ProductDetailResponse.CategoryInfo.CategoryParentInfo.builder()
                            .id(category.getCategory().getParent().getId())
                            .name(category.getCategory().getParent().getName())
                            .slug(category.getCategory().getParent().getSlug())
                            .build()
                            : null)
                        .build())
                    .collect(Collectors.toList());
            }

            List<ProductDetailResponse.OptionGroupInfo> optionGroups = null;
            if (product.getOptionGroups() != null && !product.getOptionGroups().isEmpty()) {
                optionGroups = product.getOptionGroups().stream()
                    .map(og -> ProductDetailResponse.OptionGroupInfo.builder()
                        .id(og.getId())
                        .name(og.getName())
                        .displayOrder(og.getDisplayOrder())
                        .options(og.getOptions().stream()
                            .map(opt -> ProductDetailResponse.OptionGroupInfo.OptionInfo.builder()
                                .id(opt.getId())
                                .name(opt.getName())
                                .additionalPrice(opt.getAdditionalPrice())
                                .sku(opt.getSku())
                                .stock(opt.getStock())
                                .displayOrder(opt.getDisplayOrder())
                                .build())
                            .collect(Collectors.toList()))
                        .build())
                    .collect(Collectors.toList());
            }

            List<ProductDetailResponse.ProductImageInfo> images = null;
            if (product.getImages() != null && !product.getImages().isEmpty()) {
                images = product.getImages().stream()
                    .map(img -> new ProductDetailResponse.ProductImageInfo(
                        img.getId(), img.getUrl(), img.getAltText(), img.getIsPrimary(), img.getDisplayOrder(), img.getOption().getId()))
                    .collect(Collectors.toList());
            }

            List<ProductDetailResponse.TagInfo> tags = null;
            if (product.getTags() != null && !product.getTags().isEmpty()) {
                tags = product.getTags().stream()
                    .map(tag -> new ProductDetailResponse.TagInfo(
                        tag.getId(), tag.getTag().getName(), tag.getTag().getSlug()))
                    .collect(Collectors.toList());
            }

        ProductDetailResponse.RatingInfo ratingStatistics = null;
        if (product.getReviews() != null && !product.getReviews().isEmpty()) {
            double averageRating = product.getReviews().stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

            int reviewCount = product.getReviews().size();

            Map<String, Integer> distribution = new HashMap<>();
            for (int i = 1; i <= 5; i++) {
                final int rating = i;
                int count = (int) product.getReviews().stream()
                    .filter(r -> r.getRating() == rating)
                    .count();
                distribution.put(String.valueOf(i), count);
            }

            ratingStatistics = ProductDetailResponse.RatingInfo.builder()
                .average(averageRating)
                .count(reviewCount)
                .distribution(distribution)
                .build();
        }

            return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .fullDescription(product.getFullDescription())
                .seller(sellerDetail)
                .brand(brandDetail)
                .price(productPriceInfo)
                .categories(categoryInfoList)
                .optionGroups(optionGroups)
                .images(images)
                .tags(tags)
                .rating(ratingStatistics)
                .status(product.getStatus().name())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
        }

    private ProductCreateResponse convertToCreateResponse(Product product) {
        return ProductCreateResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    private ProductUpdateResponse convertToUpdateResponse(Product product) {
        return ProductUpdateResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}