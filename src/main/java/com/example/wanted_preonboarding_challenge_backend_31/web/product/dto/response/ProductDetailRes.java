package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductStatus;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.brand.BrandDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category.CategoryInfoDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductImageDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductOptionGroupDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductPriceDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductRatingDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductRelatedDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.seller.SellerDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.tag.TagDetailDto;
import java.time.LocalDateTime;
import java.util.List;

public record ProductDetailRes(
        Long id,
        String name,
        String slug,
        String shortDescription,
        String fullDescription,
        SellerDetailDto seller,
        BrandDetailDto brand,
        ProductStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        ProductDetailDto detail,
        ProductPriceDetailDto price,
        List<CategoryInfoDto> categories,
        List<ProductOptionGroupDetailDto> optionGroups,
        List<ProductImageDetailDto> images,
        List<TagDetailDto> tags,
        ProductRatingDetailDto rating,
        List<ProductRelatedDto> relatedProducts
) {

    public static ProductDetailRes assembly(ProductDetailRes base, ProductPriceDetailDto price,
                                            List<CategoryInfoDto> categories,
                                            List<ProductOptionGroupDetailDto> optionGroups,
                                            List<ProductImageDetailDto> images, List<TagDetailDto> tags,
                                            ProductRatingDetailDto rating, List<ProductRelatedDto> relatedProducts) {
        return new ProductDetailRes(base.id, base.name, base.slug, base.shortDescription, base.fullDescription,
                base.seller, base.brand, base.status, base.createdAt, base.updatedAt, base.detail, price, categories,
                optionGroups, images, tags, rating, relatedProducts);
    }
}
