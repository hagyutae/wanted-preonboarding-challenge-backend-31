package com.preonboarding.challenge.service.query.repository;

import com.preonboarding.challenge.service.query.entity.SellerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerDocumentRepository extends MongoRepository<SellerDocument, Long> {
}