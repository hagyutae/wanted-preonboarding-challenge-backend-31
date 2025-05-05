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
    public void initIndices() {
        try {
            log.info("Initializing MongoDB indices...");

            initProductIndices();
            initSellerIndices();
            initBrandIndices();
            initTagIndices();
            initCategoryIndices();

            log.info("MongoDB indices initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize MongoDB indices", e);
        }
    }

    private void initProductIndices() {
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
    }

    private void initSellerIndices() {
        // 컬렉션 존재 확인 및 생성
        if (!mongoTemplate.collectionExists(SellerDocument.class)) {
            mongoTemplate.createCollection(SellerDocument.class);
            log.info("Created sellers collection");
        }

        // 판매자 이름 인덱스
        mongoTemplate.indexOps(SellerDocument.class)
                .ensureIndex(new Index().on("name", Sort.Direction.ASC).named("seller_name_idx"));

        // 판매자 평점 인덱스 (평점순 정렬용)
        mongoTemplate.indexOps(SellerDocument.class)
                .ensureIndex(new Index().on("rating", Sort.Direction.DESC).named("seller_rating_idx"));

        // 생성일 인덱스
        mongoTemplate.indexOps(SellerDocument.class)
                .ensureIndex(new Index().on("createdAt", Sort.Direction.DESC).named("seller_created_at_idx"));
    }

    private void initBrandIndices() {
        // 컬렉션 존재 확인 및 생성
        if (!mongoTemplate.collectionExists(BrandDocument.class)) {
            mongoTemplate.createCollection(BrandDocument.class);
            log.info("Created brands collection");
        }

        // 브랜드 이름 인덱스
        mongoTemplate.indexOps(BrandDocument.class)
                .ensureIndex(new Index().on("name", Sort.Direction.ASC).named("brand_name_idx"));

        // 브랜드 슬러그 인덱스 (URL 접근용)
        mongoTemplate.indexOps(BrandDocument.class)
                .ensureIndex(new Index().on("slug", Sort.Direction.ASC).named("brand_slug_idx"));
    }

    private void initTagIndices() {
        // 컬렉션 존재 확인 및 생성
        if (!mongoTemplate.collectionExists(TagDocument.class)) {
            mongoTemplate.createCollection(TagDocument.class);
            log.info("Created tags collection");
        }

        // 태그 이름 인덱스
        mongoTemplate.indexOps(TagDocument.class)
                .ensureIndex(new Index().on("name", Sort.Direction.ASC).named("tag_name_idx"));

        // 태그 슬러그 인덱스 (URL 접근용)
        mongoTemplate.indexOps(TagDocument.class)
                .ensureIndex(new Index().on("slug", Sort.Direction.ASC).named("tag_slug_idx"));
    }

    private void initCategoryIndices() {
        // 컬렉션 존재 확인 및 생성
        if (!mongoTemplate.collectionExists(CategoryDocument.class)) {
            mongoTemplate.createCollection(CategoryDocument.class);
            log.info("Created categories collection");
        }

        // 카테고리 이름 인덱스
        mongoTemplate.indexOps(CategoryDocument.class)
                .ensureIndex(new Index().on("name", Sort.Direction.ASC).named("category_name_idx"));

        // 카테고리 슬러그 인덱스 (URL 접근용)
        mongoTemplate.indexOps(CategoryDocument.class)
                .ensureIndex(new Index().on("slug", Sort.Direction.ASC).named("category_slug_idx"));

        // 카테고리 레벨 인덱스 (계층별 조회용)
        mongoTemplate.indexOps(CategoryDocument.class)
                .ensureIndex(new Index().on("level", Sort.Direction.ASC).named("category_level_idx"));

        // 부모 카테고리 ID 인덱스 (계층 구조 조회용)
        mongoTemplate.indexOps(CategoryDocument.class)
                .ensureIndex(new Index().on("parent.id", Sort.Direction.ASC).named("category_parent_idx"));
    }
}