package com.preonboarding.challenge.query.document;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDocumentRepository extends MongoRepository<TagDocument, Long> {
}