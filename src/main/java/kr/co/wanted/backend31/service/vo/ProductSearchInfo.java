package kr.co.wanted.backend31.service.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kr.co.wanted.backend31.common.error.ResourceNotFoundException;
import kr.co.wanted.backend31.common.model.product.Product;
import kr.co.wanted.backend31.common.model.product.ProductImage;
import kr.co.wanted.backend31.common.model.product.ProductOption;
import kr.co.wanted.backend31.common.model.product.ProductOptionGroup;
import kr.co.wanted.backend31.common.model.product.ProductReview;
import lombok.Builder;

@Builder
public record ProductSearchInfo(long id, String name, String slug, String shortDescription, BigDecimal basePrice,
        BigDecimal salePrice, String currency, ProductPrimaryImageInfo primaryImage, ProductBrandInfo brand,
        ProductSellerInfo seller, BigDecimal rating, int reviewCount, boolean inStock, String status,
        LocalDateTime createdAt) {

    public ProductSearchInfo {
        Objects.requireNonNull(name);
        Objects.requireNonNull(slug);
        Objects.requireNonNull(shortDescription);
        Objects.requireNonNull(basePrice);
        Objects.requireNonNull(salePrice);
        Objects.requireNonNull(currency);
        Objects.requireNonNull(primaryImage);
        Objects.requireNonNull(brand);
        Objects.requireNonNull(seller);
        Objects.requireNonNull(rating);
        Objects.requireNonNull(status);
        Objects.requireNonNull(createdAt);
    }

    public static ProductSearchInfo of(Product product) {
        final var primaryImage = product.getImages().stream()
                .filter(ProductImage::isPrimary)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        Map.of("reason", "product doesn't have primary image", "productId", product.getId())));

        final var reviewCount = product.getReviews().size();
        final var totalReviewRating = product.getReviews().stream()
                .map(ProductReview::getRating)
                .reduce(0, Integer::sum);

        double meanReviewRating = 0.0;
        if (reviewCount > 0) {
            meanReviewRating = Math.round(100 * totalReviewRating / reviewCount) / 100.0;
        }

        final var inStock = product.getOptionGroups().stream()
                .map(ProductOptionGroup::getOptions)
                .flatMap(List::stream)
                .map(ProductOption::getStock)
                .filter(x -> x > 0)
                .findFirst().isPresent();

        return ProductSearchInfo.builder()
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .basePrice(product.getPrice().getBasePrice())
                .salePrice(product.getPrice().getSalePrice())
                .currency(product.getPrice().getCurrency())
                .primaryImage(new ProductPrimaryImageInfo(primaryImage.getUrl(), primaryImage.getAltText()))
                .brand(new ProductBrandInfo(product.getBrand().getId(), product.getBrand().getName()))
                .seller(new ProductSellerInfo(product.getSeller().getId(), product.getSeller().getName()))
                .rating(BigDecimal.valueOf(meanReviewRating))
                .reviewCount(reviewCount)
                .inStock(inStock)
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .build();
    }

}
