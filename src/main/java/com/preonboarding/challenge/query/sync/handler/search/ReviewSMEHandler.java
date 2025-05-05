package com.preonboarding.challenge.query.sync.handler.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.search.ProductSearchDocument;
import com.preonboarding.challenge.query.search.ProductSearchRepository;
import com.preonboarding.challenge.query.sync.CdcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class ReviewSMEHandler extends ProductSearchModelEventHandler {

    private final ProductSearchRepository productSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public ReviewSMEHandler(
            ObjectMapper objectMapper,
            ProductSearchRepository productSearchRepository,
            ElasticsearchOperations elasticsearchOperations) {
        super(objectMapper);
        this.productSearchRepository = productSearchRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    protected String getSupportedTable() {
        return "reviews";
    }

    @Override
    public void handle(CdcEvent event) {
        Map<String, Object> data;
        Long productId;

        if (event.isDelete()) {
            data = event.getBeforeData();
        } else {
            data = event.getAfterData();
        }

        if (data == null || !data.containsKey("product_id")) {
            return;
        }

        productId = getLongValue(data, "product_id");

        // 평점 계산을 위해 기존 리뷰 정보 필요
        Optional<ProductSearchDocument> optionalDocument = productSearchRepository.findById(productId);
        if (optionalDocument.isEmpty()) {
            log.warn("Product document not found for review update: {}", productId);
            return;
        }

        ProductSearchDocument document = optionalDocument.get();

        // 현재 리뷰 정보 가져오기
        Double averageRating = document.getAverageRating();
        Integer reviewCount = document.getReviewCount();

        if (averageRating == null) averageRating = 0.0;
        if (reviewCount == null) reviewCount = 0;

        // 리뷰 추가, 수정, 삭제에 따른 평점 정보 업데이트
        boolean updated = false;
        if (event.isDelete()) {
            // 삭제된 리뷰의 평점
            Integer rating = getIntegerValue(data, "rating");

            // 리뷰 수 감소
            if (reviewCount > 0) {
                reviewCount--;
                updated = true;
            }

            // 평균 평점 재계산 (삭제된 평점 반영)
            if (rating != null && reviewCount > 0) {
                double totalRating = averageRating * (reviewCount + 1) - rating;
                averageRating = totalRating / reviewCount;
                updated = true;
            } else if (reviewCount == 0) {
                averageRating = 0.0;
                updated = true;
            }

        } else {
            // 추가 또는 수정된 리뷰의 평점
            Integer rating = getIntegerValue(data, "rating");

            // 이전 데이터가 있는 경우 (수정)
            if (event.isUpdate() && event.getBeforeData() != null) {
                Integer oldRating = getIntegerValue(event.getBeforeData(), "rating");

                // 평점이 실제로 변경된 경우에만 처리
                if (oldRating != null && rating != null && !oldRating.equals(rating)) {
                    double totalRating = averageRating * reviewCount - oldRating + rating;
                    averageRating = totalRating / reviewCount;
                    updated = true;
                }
            } else { // 새 리뷰
                // 리뷰 수 증가
                reviewCount++;
                updated = true;

                // 평균 평점 재계산 (새 평점 포함)
                if (rating != null) {
                    double totalRating = averageRating * (reviewCount - 1) + rating;
                    averageRating = totalRating / reviewCount;
                    updated = true;
                }
            }
        }

        // 변경된 경우에만 업데이트
        if (updated) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("averageRating", averageRating);
            updates.put("reviewCount", reviewCount);
            updatePartialDocument(productId, updates);
        }
    }

    // 부분 업데이트를 위한 메서드
    private void updatePartialDocument(Long productId, Map<String, Object> updates) {
        if (productId == null || updates == null || updates.isEmpty()) {
            return;
        }

        try {
            // Document 객체 생성
            Document document = Document.create();

            // 각 필드를 Document에 추가
            updates.forEach(document::put);

            UpdateQuery updateQuery = UpdateQuery.builder(productId.toString())
                    .withDocument(document)
                    .withDocAsUpsert(true) // 문서가 없으면 생성
                    .build();

            elasticsearchOperations.update(updateQuery, IndexCoordinates.of("products"));
            log.debug("Partially updated product review info: {}", productId);
        } catch (Exception e) {
            log.error("Error updating product review info {}: {}", productId, e.getMessage());
        }
    }
}