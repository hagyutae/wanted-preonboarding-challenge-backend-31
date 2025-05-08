package com.example.demo.product.repository;

import com.example.demo.product.dto.FeaturedCategory;
import com.example.demo.product.entity.ProductCategoryEntity;

import java.util.Collection;
import java.util.List;

public interface ProductCategoryRepositoryCustom {
    List<ProductCategoryEntity> findAllByProductIds(Collection<Long> productIds);
    List<ProductCategoryEntity> findAllByProductId(Long productId);
    Long deleteByProductId(Long productId);
    List<FeaturedCategory> findFeaturedCategories();
    List<ProductCategoryEntity> findAllByCategoryIds(List<Long> categoryIdList);
}
