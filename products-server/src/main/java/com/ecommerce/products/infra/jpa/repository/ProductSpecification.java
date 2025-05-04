package com.ecommerce.products.infra.jpa.repository;

import com.ecommerce.products.infra.jpa.entity.Product;
import com.ecommerce.products.infra.jpa.entity.ProductStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> withStatus(String status) {
        if (status == null) {
            return null;
        }
        ProductStatus productStatus = ProductStatus.valueOf(status.toUpperCase());
        return (root, query, cb) -> cb.equal(root.get("status"), productStatus);
    }

    public static Specification<Product> withMinPrice(BigDecimal minPrice) {
        if (minPrice == null) {
            return null;
        }
        return (root, query, cb) -> {
            var join = root.join("price");
            return cb.greaterThanOrEqualTo(join.get("basePrice"), minPrice);
        };
    }

    public static Specification<Product> withMaxPrice(BigDecimal maxPrice) {
        if (maxPrice == null) {
            return null;
        }
        return (root, query, cb) -> {
            var join = root.join("price");
            return cb.lessThanOrEqualTo(join.get("basePrice"), maxPrice);
        };
    }

    public static Specification<Product> withCategoryId(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> {
            var categoriesJoin = root.join("categories");
            return categoriesJoin.get("id").in(categoryIds);
        };
    }

    public static Specification<Product> withSellerId(Long sellerId) {
        if (sellerId == null) {
            return null;
        }
        return (root, query, cb) -> {
            var sellerJoin = root.join("seller");
            return cb.equal(sellerJoin.get("id"), sellerId);
        };
    }

    public static Specification<Product> withBrandId(Long brandId) {
        if (brandId == null) {
            return null;
        }
        return (root, query, cb) -> {
            var brandJoin = root.join("brand");
            return cb.equal(brandJoin.get("id"), brandId);
        };
    }

    public static Specification<Product> withTagIds(List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> {
            var tagsJoin = root.join("tags");
            return tagsJoin.get("id").in(tagIds);
        };
    }

    public static Specification<Product> inStock(Boolean inStock) {
        if (inStock == null) {
            return null;
        }

        if (inStock) {
            // 재고가 있는 상품 (적어도 하나의 옵션이 stock > 0)
            return (root, query, cb) -> {
                var optionGroupsJoin = root.join("optionGroups");
                var optionsJoin = optionGroupsJoin.join("options");
                query.distinct(true); // 중복 제거
                return cb.greaterThan(optionsJoin.get("stock"), 0);
            };
        } else {
            // 재고가 없는 상품 (모든 옵션이 stock = 0 이거나 상태가 OUT_OF_STOCK)
            return (root, query, cb) -> {
                var subquery = query.subquery(Long.class);
                var subRoot = subquery.from(Product.class);
                var subOptionGroupsJoin = subRoot.join("optionGroups");
                var subOptionsJoin = subOptionGroupsJoin.join("options");

                subquery.select(subRoot.get("id"))
                        .where(cb.and(
                                cb.equal(subRoot.get("id"), root.get("id")),
                                cb.greaterThan(subOptionsJoin.get("stock"), 0)
                        ));

                return cb.or(
                        cb.equal(root.get("status"), ProductStatus.OUT_OF_STOCK),
                        cb.not(cb.exists(subquery))
                );
            };
        }
    }

    public static Specification<Product> withSearch(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return null;
        }

        String likePattern = "%" + searchTerm.toLowerCase() + "%";

        return (root, query, cb) -> {
            query.distinct(true); // 중복 제거

            return cb.or(
                    cb.like(cb.lower(root.get("name")), likePattern),
                    cb.like(cb.lower(root.get("shortDescription")), likePattern),
                    cb.like(cb.lower(root.get("fullDescription")), likePattern)
            );
        };
    }

    public static Specification<Product> withCreatedDateAfter(LocalDateTime date) {
        if (date == null) {
            return null;
        }

        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createdAt"), date);
    }

    public static Specification<Product> withCreatedDateBefore(LocalDateTime date) {
        if (date == null) {
            return null;
        }

        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("createdAt"), date);
    }
}