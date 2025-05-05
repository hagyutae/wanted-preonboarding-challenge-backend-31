package com.preonboarding.challenge.service.query;

import com.preonboarding.challenge.service.query.entity.ProductSearchDocument;
import org.springframework.data.elasticsearch.core.SearchHits;

public interface ProductSearchOperations {

    SearchHits<ProductSearchDocument> searchProductsByConditions(ProductQuery.ListProducts query);
}
