package com.preonboarding.challenge.service.query.sync;

import com.preonboarding.challenge.service.query.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MongoDBInitService {

    private final MongoTemplate mongoTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void initCollections() {
        try {
            log.info("Initializing MongoDB collections...");

            initProductCollection();
            initSellerCollection();
            initBrandCollection();
            initTagCollection();
            initCategoryCollection();

            log.info("MongoDB collections initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize MongoDB collections", e);
        }
    }

    private void initProductCollection() {
        // 컬렉션 존재 확인 및 생성
        if (!mongoTemplate.collectionExists(ProductDocument.class)) {
            mongoTemplate.createCollection(ProductDocument.class);
            log.info("Created products collection");
        }
    }

    private void initSellerCollection() {
        // 컬렉션 존재 확인 및 생성
        if (!mongoTemplate.collectionExists(SellerDocument.class)) {
            mongoTemplate.createCollection(SellerDocument.class);
            log.info("Created sellers collection");
        }
    }

    private void initBrandCollection() {
        // 컬렉션 존재 확인 및 생성
        if (!mongoTemplate.collectionExists(BrandDocument.class)) {
            mongoTemplate.createCollection(BrandDocument.class);
            log.info("Created brands collection");
        }
    }

    private void initTagCollection() {
        // 컬렉션 존재 확인 및 생성
        if (!mongoTemplate.collectionExists(TagDocument.class)) {
            mongoTemplate.createCollection(TagDocument.class);
            log.info("Created tags collection");
        }
    }

    private void initCategoryCollection() {
        // 컬렉션 존재 확인 및 생성
        if (!mongoTemplate.collectionExists(CategoryDocument.class)) {
            mongoTemplate.createCollection(CategoryDocument.class);
            log.info("Created categories collection");
        }
        // 카테고리 레벨 인덱스 (계층별 조회용)
        mongoTemplate.indexOps(CategoryDocument.class)
                .ensureIndex(new Index().on("level", Sort.Direction.ASC).named("category_level_idx"));

        // 부모 카테고리 ID 인덱스 (계층 구조 조회용)
        mongoTemplate.indexOps(CategoryDocument.class)
                .ensureIndex(new Index().on("parent.id", Sort.Direction.ASC).named("category_parent_idx"));
    }
}