package wanted.domain.product.dto.response;

import wanted.domain.brand.dto.response.BrandResponse;
import wanted.domain.category.dto.response.CategoryResponse;
import wanted.domain.product.entity.Product;
import wanted.domain.seller.dto.response.SellerResponse;
import wanted.domain.tag.dto.response.TagResponse;

import java.time.LocalDateTime;
import java.util.List;

public record ProductDetailResponse(
        Long id,
        String name,
        String slug,
        String shortDescription,
        String fullDescription,
        SellerResponse seller,
        BrandResponse brand,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        ProductDetailInfoResponse detail,
        ProductPriceResponse price,
        List<CategoryResponse> categories,
        List<ProductOptionGroupResponse> optionGroups,
        List<ProductImageResponse> images,
        List<TagResponse> tags,
        ProductRatingResponse rating,
        List<RelatedProductResponse> relatedProducts
) {
    public static ProductDetailResponse of(Product product, ProductRatingResponse rating, List<Product> relatedProducts) {
        return new ProductDetailResponse(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getShortDescription(),
                product.getFullDescription(),
                SellerResponse.of(product.getSeller()),
                BrandResponse.of(product.getBrand()),
                product.getStatus().name(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                ProductDetailInfoResponse.of(product.getProductDetail()),
                ProductPriceResponse.of(product.getProductPrice()),
                product.getProductCategories().stream()
                        .map(pc -> CategoryResponse.of(pc.getCategory(), pc.isPrimary()))
                        .toList(),
                product.getOptionGroups().stream()
                        .map(ProductOptionGroupResponse::of)
                        .toList(),
                product.getImages().stream()
                        .map(ProductImageResponse::of)
                        .toList(),
                product.getTags().stream()
                        .map(pt -> TagResponse.of(pt.getTag()))
                        .toList(),
                rating,
                relatedProducts.stream()
                        .map(RelatedProductResponse::of)
                        .toList()
        );
    }

}
