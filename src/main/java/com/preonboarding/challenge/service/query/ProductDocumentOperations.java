package com.preonboarding.challenge.service.query;

import com.preonboarding.challenge.service.dto.MainPageDto;
import com.preonboarding.challenge.service.query.entity.ProductDocument;

import java.util.List;

public interface ProductDocumentOperations {

    ProductDocument findProductDocumentWithReferences(Long productId);

    List<ProductDocument> findProductDocumentsWithReferences(List<Long> productIds);

    List<ProductDocument> findNewProducts(int limit);

    List<ProductDocument> findPopularProducts(int limit);

    List<MainPageDto.FeaturedCategory> findFeaturedCategories(int limit);
}
