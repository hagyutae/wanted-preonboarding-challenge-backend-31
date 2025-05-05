package com.preonboarding.challenge.service.query.repository;

import com.preonboarding.challenge.service.query.entity.TagDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDocumentRepository extends MongoRepository<TagDocument, Long> {
}