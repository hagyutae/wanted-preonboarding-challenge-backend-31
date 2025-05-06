package com.sandro.wanted_shop.product.dto;

import com.sandro.wanted_shop.brand.Brand;
import com.sandro.wanted_shop.product.entity.Product;
import com.sandro.wanted_shop.product.entity.ProductDetail;
import com.sandro.wanted_shop.product.entity.ProductPrice;
import com.sandro.wanted_shop.product.entity.enums.Currency;
import com.sandro.wanted_shop.product.entity.enums.ProductStatus;
import com.sandro.wanted_shop.seller.Seller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record CreateProductCommand(
        String name,
        String slug,
        String shortDescription,
        String fullDescription,
        Long sellerId,
        Long brandId,
        ProductStatus status,
        Price price,
        Detail detail,
        List<Category> categories,
        List<CreateOptionGroupCommand> options,
        List<CreateImageCommand> images,
        List<Long> tagIdList
) {
    public List<Long> categoryIdList() {
        return categories.stream()
                .map(category -> category.id)
                .toList();
    }

    public Product toEntity(Seller seller, Brand brand) {
        Product product = Product.builder()
                .name(name)
                .slug(slug)
                .shortDescription(shortDescription)
                .fullDescription(fullDescription)
                .seller(seller)
                .brand(brand)
                .status(status)
                .build();
        price.toEntity(product);
        detail.toEntity(product);
        return product;
    }

    public record Category(
            Long id,
            Boolean isPrimary
    ) {
    }

    public record Price(
            BigDecimal basePrice,
            BigDecimal salePrice,
            BigDecimal costPrice,
            Currency currency,
            BigDecimal taxRate
    ) {
        public ProductPrice toEntity(Product product) {
            return ProductPrice.builder()
                    .product(product)
                    .basePrice(basePrice)
                    .salePrice(salePrice)
                    .costPrice(costPrice)
                    .currency(currency)
                    .taxRate(taxRate)
                    .build();
        }
    }

    public record Detail(
            BigDecimal weight,
            Dimensions dimensions,
            String materials,
            String countryOfOrigin,
            String warrantyInfo,
            String careInstructions,
            Map<String, Object> additionalInfo
    ) {
        public ProductDetail toEntity(Product product) {
            return ProductDetail.builder()
                    .product(product)
                    .weight(weight)
                    .dimensions(dimensions)
                    .materials(materials)
                    .countryOfOrigin(countryOfOrigin)
                    .warrantyInfo(warrantyInfo)
                    .careInstructions(careInstructions)
                    .additionalInfo(additionalInfo)
                    .build();
        }
    }
}
