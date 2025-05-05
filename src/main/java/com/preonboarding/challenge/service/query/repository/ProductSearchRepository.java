package com.preonboarding.challenge.service.query.repository;

import com.preonboarding.challenge.service.query.entity.ProductSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearchDocument, Long> {
}