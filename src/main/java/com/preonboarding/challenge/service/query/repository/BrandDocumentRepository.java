package com.preonboarding.challenge.service.query.repository;

import com.preonboarding.challenge.service.query.entity.BrandDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandDocumentRepository extends MongoRepository<BrandDocument, Long> {
}