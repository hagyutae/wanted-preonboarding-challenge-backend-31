package com.wanted.mono.domain.product.service;

import com.wanted.mono.domain.product.dto.BrandAndSellerItem;
import com.wanted.mono.domain.product.dto.ProductImageDto;
import com.wanted.mono.domain.product.dto.ProductSearchItem;
import com.wanted.mono.domain.product.entity.Product;
import com.wanted.mono.domain.product.entity.ProductImage;
import com.wanted.mono.domain.review.entity.Review;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProductItemService {
    public ProductSearchItem toDto(Product product) {
        ProductImage primaryImage = product.getProductImages().stream()
                .filter(ProductImage::getIsPrimary)
                .findFirst()
                .orElse(null);

        double rating = product.getReviews().stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        Long reviewCount = (long) product.getReviews().size();

        boolean inStock = Objects.equals(product.getStatus(), "ACTIVE");

        return new ProductSearchItem(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getShortDescription(),
                product.getProductPrices().get(0).getBasePrice(),
                product.getProductPrices().get(0).getSalePrice(),
                "KRW",
                primaryImage == null ? null :
                        new ProductImageDto(primaryImage.getUrl(), primaryImage.getAltText()),
                new BrandAndSellerItem(product.getBrand().getId(), product.getBrand().getName()),
                new BrandAndSellerItem(product.getSeller().getId(), product.getSeller().getName()),
                rating,
                reviewCount,
                inStock,
                product.getStatus(),
                product.getCreatedAt()
        );
    }
}
