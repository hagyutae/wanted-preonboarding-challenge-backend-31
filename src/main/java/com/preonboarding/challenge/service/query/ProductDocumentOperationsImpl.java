package com.preonboarding.challenge.service.query;

import com.preonboarding.challenge.service.query.entity.ProductDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDocumentOperationsImpl implements ProductDocumentOperations {

    private final MongoTemplate mongoTemplate;

    @Override
    public ProductDocument findProductDocumentWithReferences(Long productId) {
        try {
            // 직접 Aggregation 파이프라인을 구성 - MongoDB의 기본 Document 사용
            List<Document> pipeline = buildAggregatePipeline(productId);

            // MongoDB에서 결과 조회
            Document result = mongoTemplate.getCollection("products")
                    .aggregate(pipeline)
                    .first();

            if (result == null) {
                return null;
            }

            // Document에서 ProductDocument로 변환
            ProductDocument productDocument = mongoTemplate.getConverter()
                    .read(ProductDocument.class, result);

            return productDocument;
        } catch (Exception e) {
            log.error("Error finding product document: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<ProductDocument> findProductDocumentsWithReferences(List<Long> productIds) {
        if (productIds.isEmpty()) {
            return Collections.emptyList();
        }

        try {
            // 직접 Aggregation 파이프라인을 구성
            List<Document> pipeline = buildAggregatePipelineForMultiple(productIds);

            // MongoDB에서 결과 조회
            List<Document> results = new ArrayList<>();
            mongoTemplate.getCollection("products")
                    .aggregate(pipeline)
                    .into(results);

            if (results.isEmpty()) {
                return Collections.emptyList();
            }

            // Document 목록에서 ProductDocument 목록으로 변환
            List<ProductDocument> productDocuments = new ArrayList<>();
            for (Document doc : results) {
                ProductDocument productDocument = mongoTemplate.getConverter()
                        .read(ProductDocument.class, doc);
                productDocuments.add(productDocument);
            }

            // ID 순서에 맞게 정렬
            Map<Long, ProductDocument> productMap = new HashMap<>();
            for (ProductDocument product : productDocuments) {
                productMap.put(product.getId(), product);
            }

            List<ProductDocument> orderedResults = new ArrayList<>(productIds.size());
            for (Long id : productIds) {
                ProductDocument product = productMap.get(id);
                if (product != null) {
                    orderedResults.add(product);
                }
            }

            return orderedResults;
        } catch (Exception e) {
            log.error("Error finding product documents: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * 단일 상품을 위한 MongoDB Aggregation 파이프라인 구성
     */
    private List<Document> buildAggregatePipeline(Long productId) {
        List<Document> pipeline = new ArrayList<>();

        // 1. 상품 ID로 필터링
        pipeline.add(new Document("$match", new Document("_id", productId)));

        // 공통 Aggregation 추가
        pipeline.addAll(commonAggregatePipeline());

        return pipeline;
    }

    /**
     * 여러 상품을 위한 MongoDB Aggregation 파이프라인 구성
     */
    private List<Document> buildAggregatePipelineForMultiple(List<Long> productIds) {
        List<Document> pipeline = new ArrayList<>();

        // 1. 상품 ID 목록으로 필터링
        pipeline.add(new Document("$match", new Document("_id", new Document("$in", productIds))));

        // 공통 Aggregation 추가
        pipeline.addAll(commonAggregatePipeline());

        return pipeline;
    }

    /**
     * 공통 MongoDB Aggregation 파이프라인 단계
     */
    private List<Document> commonAggregatePipeline() {
        List<Document> pipeline = new ArrayList<>();

        // 1. 브랜드 정보 조인
        pipeline.add(new Document("$lookup",
                new Document("from", "brands")
                        .append("localField", "brand._id")
                        .append("foreignField", "_id")
                        .append("as", "brandDetails")));

        // 브랜드 정보 매핑
        pipeline.add(new Document("$addFields",
                new Document("brand",
                        new Document("$cond", Arrays.asList(
                                new Document("$gt", Arrays.asList(
                                        new Document("$size", "$brandDetails"), 0)),
                                new Document("$mergeObjects", Arrays.asList(
                                        "$brand",
                                        new Document("name", new Document("$arrayElemAt", Arrays.asList("$brandDetails.name", 0))),
                                        new Document("slug", new Document("$arrayElemAt", Arrays.asList("$brandDetails.slug", 0)))
                                )),
                                "$brand"
                        ))
                )));

        // 2. 판매자 정보 조인
        pipeline.add(new Document("$lookup",
                new Document("from", "sellers")
                        .append("localField", "seller._id")
                        .append("foreignField", "_id")
                        .append("as", "sellerDetails")));

        // 판매자 정보 매핑
        pipeline.add(new Document("$addFields",
                new Document("seller",
                        new Document("$cond", Arrays.asList(
                                new Document("$gt", Arrays.asList(
                                        new Document("$size", "$sellerDetails"), 0)),
                                new Document("$mergeObjects", Arrays.asList(
                                        "$seller",
                                        new Document("name", new Document("$arrayElemAt", Arrays.asList("$sellerDetails.name", 0)))
                                )),
                                "$seller"
                        ))
                )));

        // 3. 태그 정보 조인
        pipeline.add(new Document("$lookup",
                new Document("from", "tags")
                        .append("localField", "tags._id")
                        .append("foreignField", "_id")
                        .append("as", "tagDetails")));

        // 태그 정보 매핑
        pipeline.add(new Document("$addFields",
                new Document("tags",
                        new Document("$cond", Arrays.asList(
                                new Document("$isArray", "$tags"),
                                new Document("$map",
                                        new Document("input", "$tags")
                                                .append("as", "tag")
                                                .append("in",
                                                        new Document("$mergeObjects", Arrays.asList(
                                                                "$$tag",
                                                                new Document("$let",
                                                                        new Document("vars",
                                                                                new Document("matchedTag",
                                                                                        new Document("$arrayElemAt", Arrays.asList(
                                                                                                new Document("$filter",
                                                                                                        new Document("input", "$tagDetails")
                                                                                                                .append("as", "t")
                                                                                                                .append("cond",
                                                                                                                        new Document("$eq", Arrays.asList("$$t._id", "$$tag._id"))
                                                                                                                )
                                                                                                ),
                                                                                                0
                                                                                        ))
                                                                                )
                                                                        )
                                                                                .append("in",
                                                                                        new Document("$cond", Arrays.asList(
                                                                                                "$$matchedTag",
                                                                                                new Document()
                                                                                                        .append("name", "$$matchedTag.name")
                                                                                                        .append("slug", "$$matchedTag.slug"),
                                                                                                new Document()
                                                                                        ))
                                                                                )
                                                                )
                                                        ))
                                                )
                                ),
                                "$tags"
                        ))
                )));

        // 4. 카테고리 정보 조인
        pipeline.add(new Document("$lookup",
                new Document("from", "categories")
                        .append("localField", "categories._id")
                        .append("foreignField", "_id")
                        .append("as", "categoryDetails")));

        // 카테고리 정보 매핑
        pipeline.add(new Document("$addFields",
                new Document("categories",
                        new Document("$cond", Arrays.asList(
                                new Document("$isArray", "$categories"),
                                new Document("$map",
                                        new Document("input", "$categories")
                                                .append("as", "category")
                                                .append("in",
                                                        new Document("$mergeObjects", Arrays.asList(
                                                                "$$category",
                                                                new Document("$let",
                                                                        new Document("vars",
                                                                                new Document("matchedCategory",
                                                                                        new Document("$arrayElemAt", Arrays.asList(
                                                                                                new Document("$filter",
                                                                                                        new Document("input", "$categoryDetails")
                                                                                                                .append("as", "c")
                                                                                                                .append("cond",
                                                                                                                        new Document("$eq", Arrays.asList("$$c._id", "$$category._id"))
                                                                                                                )
                                                                                                ),
                                                                                                0
                                                                                        ))
                                                                                )
                                                                        )
                                                                                .append("in",
                                                                                        new Document("$cond", Arrays.asList(
                                                                                                "$$matchedCategory",
                                                                                                new Document()
                                                                                                        .append("name", "$$matchedCategory.name")
                                                                                                        .append("slug", "$$matchedCategory.slug")
                                                                                                        .append("parent", "$$matchedCategory.parent"),
                                                                                                new Document()
                                                                                        ))
                                                                                )
                                                                )
                                                        ))
                                                )
                                ),
                                "$categories"
                        ))
                )));

        // 5. 임시 조인 컬렉션 제거
        pipeline.add(new Document("$project",
                new Document("brandDetails", 0)
                        .append("sellerDetails", 0)
                        .append("categoryDetails", 0)
                        .append("tagDetails", 0)));

        return pipeline;
    }
}