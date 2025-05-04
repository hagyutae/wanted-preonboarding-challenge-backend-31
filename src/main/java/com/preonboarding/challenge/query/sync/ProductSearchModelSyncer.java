package com.preonboarding.challenge.query.sync;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.search.ProductSearchDocument;
import com.preonboarding.challenge.query.search.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSearchModelSyncer {

    private final ObjectMapper objectMapper;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ProductSearchRepository productSearchRepository;

    @KafkaListener(topics = {"product-events"}, groupId = "product-search-group")
    public void consumeProductEvents(
            @Payload String messageValue,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {

        try {
            // 디버깅을 위한 원본 메시지 로깅
            log.debug("Received CDC event - Key: {}, Value: {}", messageKey, messageValue);

            // 값 데이터 파싱
            CdcEvent event = objectMapper.readValue(messageValue, CdcEvent.class);

            // 키 데이터 파싱 및 설정
            if (messageKey != null && !messageKey.isEmpty()) {
                Map<String, Object> keyData = objectMapper.readValue(messageKey,
                        new TypeReference<Map<String, Object>>() {});
                event.setKey(keyData);
            }

            String table = event.getTable();
            if (table == null) {
                log.warn("Table name missing in event");
                return;
            }

            // 이벤트 타입에 따라 처리
            switch (table) {
                case "products":
                    handleProductEvent(event);
                    break;
                case "product_prices":
                    handleProductPriceEvent(event);
                    break;
                case "product_categories":
                    handleProductCategoryEvent(event);
                    break;
                case "product_tags":
                    handleProductTagEvent(event);
                    break;
                case "reviews":
                    handleReviewEvent(event);
                    break;
                case "product_details":
                    handleProductDetailEvent(event);
                    break;
                default:
                    log.debug("Ignoring event for table: {}", table);
            }

        } catch (JsonProcessingException e) {
            log.error("Error parsing CDC event: {}", messageValue, e);
        } catch (Exception e) {
            log.error("Error processing CDC event: {}", messageValue, e);
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
            log.debug("Partially updated document: {}", productId);
        } catch (Exception e) {
            log.error("Error updating document {}: {}", productId, e.getMessage());
        }
    }

    private void handleProductEvent(CdcEvent event) {
        Map<String, Object> data;
        Long productId;

        if (event.isDelete()) {
            // 삭제 이벤트 처리
            data = event.getBeforeData();
            if (data == null || !data.containsKey("id")) {
                return;
            }

            productId = getLongValue(data, "id");
            productSearchRepository.deleteById(productId);
            return;
        }

        // 생성 또는 업데이트 이벤트 처리
        data = event.getAfterData();
        if (data == null || !data.containsKey("id")) {
            return;
        }

        productId = getLongValue(data, "id");

        // 전체 문서 생성/업데이트를 위해 새 문서 생성
        // products 테이블 이벤트는 항상 전체 문서로 처리
        ProductSearchDocument document = new ProductSearchDocument();
        document.setId(productId);
        document.setName(getStringValue(data, "name"));
        document.setSlug(getStringValue(data, "slug"));
        document.setShortDescription(getStringValue(data, "short_description"));
        document.setFullDescription(getStringValue(data, "full_description"));
        document.setStatus(getStringValue(data, "status"));
        document.setSellerId(getLongValue(data, "seller_id"));
        document.setBrandId(getLongValue(data, "brand_id"));

        // 날짜 필드 처리
        if (data.containsKey("created_at")) {
            document.setCreatedAt(parseTimestampToInstant(data.get("created_at")));
        }

        if (data.containsKey("updated_at")) {
            document.setUpdatedAt(parseTimestampToInstant(data.get("updated_at")));
        }

        // 재고 상태 업데이트 - Product.status 기준
        document.setInStock("ACTIVE".equals(getStringValue(data, "status")));

        // 기존 문서가 있을 경우 누락된 필드 복원
        Optional<ProductSearchDocument> existingDoc = productSearchRepository.findById(productId);
        if (existingDoc.isPresent()) {
            ProductSearchDocument existing = existingDoc.get();
            // 기존 필드 복원
            document.setMaterials(existing.getMaterials());
            document.setBasePrice(existing.getBasePrice());
            document.setSalePrice(existing.getSalePrice());
            document.setCategoryIds(existing.getCategoryIds());
            document.setTagIds(existing.getTagIds());
            document.setAverageRating(existing.getAverageRating());
            document.setReviewCount(existing.getReviewCount());
        }

        // 저장
        productSearchRepository.save(document);
    }

    private void handleProductDetailEvent(CdcEvent event) {
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

        // 삭제 이벤트 처리
        if (event.isDelete()) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("materials", null);
            updatePartialDocument(productId, updates);
            return;
        }

        // 부분 업데이트로 처리
        if (data.containsKey("materials")) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("materials", getStringValue(data, "materials"));
            updatePartialDocument(productId, updates);
        }
    }

    private void handleProductPriceEvent(CdcEvent event) {
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

        // 삭제 이벤트 처리
        if (event.isDelete()) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("basePrice", null);
            updates.put("salePrice", null);
            updatePartialDocument(productId, updates);
            return;
        }

        // 부분 업데이트로 처리
        Map<String, Object> updates = new HashMap<>();

        if (data.containsKey("base_price")) {
            updates.put("basePrice", getBigDecimalValue(data, "base_price"));
        }

        if (data.containsKey("sale_price")) {
            updates.put("salePrice", getBigDecimalValue(data, "sale_price"));
        }

        if (!updates.isEmpty()) {
            updatePartialDocument(productId, updates);
        }
    }

    private void handleProductCategoryEvent(CdcEvent event) {
        Map<String, Object> data;
        Long productId;

        if (event.isDelete()) {
            data = event.getBeforeData();
        } else {
            data = event.getAfterData();
        }

        if (data == null || !data.containsKey("product_id") || !data.containsKey("category_id")) {
            return;
        }

        productId = getLongValue(data, "product_id");
        Long categoryId = getLongValue(data, "category_id");

        // 카테고리 목록을 조회해야 함 - 기존 문서 필요
        Optional<ProductSearchDocument> optionalDocument = productSearchRepository.findById(productId);
        if (optionalDocument.isEmpty()) {
            log.warn("Product document not found for category update: {}", productId);
            return;
        }

        ProductSearchDocument document = optionalDocument.get();

        // 기존 카테고리 목록 가져오기
        List<Long> categoryIds = document.getCategoryIds();
        if (categoryIds == null) {
            categoryIds = new ArrayList<>();
        }

        // 카테고리 매핑 추가 또는 제거
        boolean updated = false;
        if (event.isDelete()) {
            updated = categoryIds.remove(categoryId);
        } else if (!categoryIds.contains(categoryId)) {
            categoryIds.add(categoryId);
            updated = true;
        }

        // 변경된 경우에만 업데이트
        if (updated) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("categoryIds", categoryIds);
            updatePartialDocument(productId, updates);
        }
    }

    private void handleProductTagEvent(CdcEvent event) {
        Map<String, Object> data;
        Long productId;

        if (event.isDelete()) {
            data = event.getBeforeData();
        } else {
            data = event.getAfterData();
        }

        if (data == null || !data.containsKey("product_id") || !data.containsKey("tag_id")) {
            return;
        }

        productId = getLongValue(data, "product_id");
        Long tagId = getLongValue(data, "tag_id");

        // 태그 목록을 조회해야 함 - 기존 문서 필요
        Optional<ProductSearchDocument> optionalDocument = productSearchRepository.findById(productId);
        if (optionalDocument.isEmpty()) {
            log.warn("Product document not found for tag update: {}", productId);
            return;
        }

        ProductSearchDocument document = optionalDocument.get();

        // 기존 태그 목록 가져오기
        List<Long> tagIds = document.getTagIds();
        if (tagIds == null) {
            tagIds = new ArrayList<>();
        }

        // 태그 매핑 추가 또는 제거
        boolean updated = false;
        if (event.isDelete()) {
            updated = tagIds.remove(tagId);
        } else if (!tagIds.contains(tagId)) {
            tagIds.add(tagId);
            updated = true;
        }

        // 변경된 경우에만 업데이트
        if (updated) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("tagIds", tagIds);
            updatePartialDocument(productId, updates);
        }
    }

    private void handleReviewEvent(CdcEvent event) {
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

    // 유틸리티 메서드
    private String getStringValue(Map<String, Object> data, String key) {
        return data.containsKey(key) && data.get(key) != null ? data.get(key).toString() : null;
    }

    private Long getLongValue(Map<String, Object> data, String key) {
        if (data.containsKey(key) && data.get(key) != null) {
            Object value = data.get(key);
            if (value instanceof Number) {
                return ((Number) value).longValue();
            } else {
                try {
                    return Long.valueOf(value.toString());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    private Integer getIntegerValue(Map<String, Object> data, String key) {
        if (data.containsKey(key) && data.get(key) != null) {
            Object value = data.get(key);
            if (value instanceof Number) {
                return ((Number) value).intValue();
            } else {
                try {
                    return Integer.valueOf(value.toString());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    private BigDecimal getBigDecimalValue(Map<String, Object> data, String key) {
        if (data.containsKey(key) && data.get(key) != null) {
            Object value = data.get(key);
            if (value instanceof Number) {
                return new BigDecimal(value.toString());
            } else {
                try {
                    return new BigDecimal(value.toString());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    private Instant parseTimestampToInstant(Object timestamp) {
        if (timestamp == null) {
            return null;
        }

        if (timestamp instanceof String) {
            try {
                return LocalDateTime.parse((String) timestamp).atZone(ZoneId.systemDefault()).toInstant();
            } catch (Exception e) {
                log.warn("Failed to parse timestamp string: {}", timestamp);
                return null;
            }
        } else if (timestamp instanceof Number) {
            try {
                // 마이크로초 단위를 밀리초로 변환 (1/1000)
                long microseconds = ((Number) timestamp).longValue();
                long milliseconds = microseconds / 1000;

                return Instant.ofEpochMilli(milliseconds);
            } catch (Exception e) {
                log.warn("Failed to parse timestamp number: {}", timestamp);
                return null;
            }
        }

        return null;
    }
}