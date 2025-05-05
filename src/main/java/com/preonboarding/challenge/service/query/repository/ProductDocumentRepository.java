package com.preonboarding.challenge.service.query.repository;

import com.preonboarding.challenge.service.query.entity.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDocumentRepository extends MongoRepository<ProductDocument, Long> {
}