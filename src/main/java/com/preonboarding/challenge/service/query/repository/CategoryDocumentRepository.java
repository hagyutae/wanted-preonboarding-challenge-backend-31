package com.preonboarding.challenge.service.query.repository;

import com.preonboarding.challenge.service.query.entity.CategoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDocumentRepository extends MongoRepository<CategoryDocument, Long> {

    @Query("{'level': ?0}")
    List<CategoryDocument> findByLevel(Integer level);

    @Query("{'parent.id': ?0}")
    List<CategoryDocument> findByParentId(Long parentId);
}