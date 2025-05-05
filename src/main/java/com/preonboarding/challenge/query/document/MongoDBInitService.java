package com.preonboarding.challenge.query.document;

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
    public void initIndices() {
        try {
            log.info("Initializing MongoDB indices...");

            // 컬렉션 존재 확인 및 생성
            if (!mongoTemplate.collectionExists(ProductDocument.class)) {
                mongoTemplate.createCollection(ProductDocument.class);
                log.info("Created products collection");
            }

            // 인덱스 생성
            // 상품 이름 인덱스 (텍스트 검색용)
            mongoTemplate.indexOps(ProductDocument.class)
                    .ensureIndex(new Index().on("name", Sort.Direction.ASC).named("name_idx"));

            // 상품 상태 인덱스 (상태별 필터링용)
            mongoTemplate.indexOps(ProductDocument.class)
                    .ensureIndex(new Index().on("status", Sort.Direction.ASC).named("status_idx"));

            // 생성일 인덱스 (최신순 정렬용)
            mongoTemplate.indexOps(ProductDocument.class)
                    .ensureIndex(new Index().on("createdAt", Sort.Direction.DESC).named("created_at_idx"));

            // 평점 인덱스 (평점순 정렬용)
            mongoTemplate.indexOps(ProductDocument.class)
                    .ensureIndex(new Index().on("rating.average", Sort.Direction.DESC).named("rating_avg_idx"));

            // 가격 인덱스 (가격순 정렬용)
            mongoTemplate.indexOps(ProductDocument.class)
                    .ensureIndex(new Index().on("price.salePrice", Sort.Direction.ASC).named("price_idx"));

            // 카테고리 인덱스 (카테고리별 필터링용)
            mongoTemplate.indexOps(ProductDocument.class)
                    .ensureIndex(new Index().on("categories.id", Sort.Direction.ASC).named("category_idx"));

            // 브랜드 인덱스 (브랜드별 필터링용)
            mongoTemplate.indexOps(ProductDocument.class)
                    .ensureIndex(new Index().on("brand.id", Sort.Direction.ASC).named("brand_idx"));

            // 태그 인덱스 (태그별 필터링용)
            mongoTemplate.indexOps(ProductDocument.class)
                    .ensureIndex(new Index().on("tags.id", Sort.Direction.ASC).named("tag_idx"));

            log.info("MongoDB indices initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize MongoDB indices", e);
        }
    }
}