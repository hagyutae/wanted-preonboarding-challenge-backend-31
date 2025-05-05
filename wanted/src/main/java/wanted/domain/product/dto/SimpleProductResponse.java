package wanted.domain.product.dto;

import wanted.domain.product.entity.*;
        import wanted.domain.review.entity.Review;
import wanted.domain.seller.entity.Seller;
import wanted.domain.brand.entity.Brand;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SimpleProductResponse(
        Long id,
        String name,
        String slug,
        String shortDescription,
        BigDecimal basePrice,
        BigDecimal salePrice,
        String currency,
        ImageDto primaryImage,
        BrandDto brand,
        SellerDto seller,
        Double rating,
        int reviewCount,
        boolean inStock,
        String status,
        LocalDateTime createdAt
) {

    public static SimpleProductResponse from(Product product) {
        ProductPrice price = product.getProductPrice();
        List<Review> reviews = product.getReviews();

        return new SimpleProductResponse(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getShortDescription(),
                price != null ? price.getBasePrice() : null,
                price != null ? price.getSalePrice() : null,
                price != null ? price.getCurrency() : null,
                extractPrimaryImage(product),
                BrandDto.from(product.getBrand()),
                SellerDto.from(product.getSeller()),
                calculateAverageRating(reviews),
                reviews != null ? reviews.size() : 0,
                checkInStock(product),
                product.getStatus().name(),
                product.getCreatedAt()
        );
    }

    private static ImageDto extractPrimaryImage(Product product) {
        return product.getImages().stream()
                .filter(ProductImage::isPrimary)
                .findFirst()
                .map(img -> new ImageDto(img.getUrl(), img.getAltText()))
                .orElse(null);
    }

    private static boolean checkInStock(Product product) {
        return product.getOptionGroups().stream()
                .flatMap(group -> group.getOptions().stream())
                .anyMatch(option -> option.getStock() > 0);
    }

    private static Double calculateAverageRating(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) return 0.0;
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }

    public record ImageDto(String url, String altText) {}
    public record BrandDto(Long id, String name) {
        public static BrandDto from(Brand brand) {
            return brand != null ? new BrandDto(brand.getId(), brand.getName()) : null;
        }
    }
    public record SellerDto(Long id, String name) {
        public static SellerDto from(Seller seller) {
            return seller != null ? new SellerDto(seller.getId(), seller.getName()) : null;
        }
    }
}

