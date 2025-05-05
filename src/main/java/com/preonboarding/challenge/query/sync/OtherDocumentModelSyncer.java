package com.preonboarding.challenge.query.sync;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.document.*;
import com.preonboarding.challenge.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtherDocumentModelSyncer {

    private final ObjectMapper objectMapper;
    private final SellerDocumentRepository sellerDocumentRepository;
    private final BrandDocumentRepository brandDocumentRepository;
    private final TagDocumentRepository tagDocumentRepository;
    private final CategoryDocumentRepository categoryDocumentRepository;
    private final CategoryRepository categoryRepository; // JPA repository for fetching related entities

    @KafkaListener(topics = {"seller-events"}, groupId = "seller-document-group")
    public void consumeSellerEvents(
            @Payload String messageValue,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {

        try {
            log.debug("Received seller CDC event - Key: {}, Value: {}", messageKey, messageValue);

            // 값 데이터 파싱
            CdcEvent event = objectMapper.readValue(messageValue, CdcEvent.class);

            // 키 데이터 파싱 및 설정
            if (messageKey != null && !messageKey.isEmpty()) {
                Map<String, Object> keyData = objectMapper.readValue(messageKey,
                        new TypeReference<Map<String, Object>>() {});
                event.setKey(keyData);
            }

            handleSellerEvent(event);

        } catch (JsonProcessingException e) {
            log.error("Error parsing seller CDC event: {}", messageValue, e);
        } catch (Exception e) {
            log.error("Error processing seller CDC event: {}", messageValue, e);
        }
    }

    @KafkaListener(topics = {"brand-events"}, groupId = "brand-document-group")
    public void consumeBrandEvents(
            @Payload String messageValue,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {

        try {
            log.debug("Received brand CDC event - Key: {}, Value: {}", messageKey, messageValue);

            // 값 데이터 파싱
            CdcEvent event = objectMapper.readValue(messageValue, CdcEvent.class);

            // 키 데이터 파싱 및 설정
            if (messageKey != null && !messageKey.isEmpty()) {
                Map<String, Object> keyData = objectMapper.readValue(messageKey,
                        new TypeReference<Map<String, Object>>() {});
                event.setKey(keyData);
            }

            handleBrandEvent(event);

        } catch (JsonProcessingException e) {
            log.error("Error parsing brand CDC event: {}", messageValue, e);
        } catch (Exception e) {
            log.error("Error processing brand CDC event: {}", messageValue, e);
        }
    }

    @KafkaListener(topics = {"tag-events"}, groupId = "tag-document-group")
    public void consumeTagEvents(
            @Payload String messageValue,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {

        try {
            log.debug("Received tag CDC event - Key: {}, Value: {}", messageKey, messageValue);

            // 값 데이터 파싱
            CdcEvent event = objectMapper.readValue(messageValue, CdcEvent.class);

            // 키 데이터 파싱 및 설정
            if (messageKey != null && !messageKey.isEmpty()) {
                Map<String, Object> keyData = objectMapper.readValue(messageKey,
                        new TypeReference<Map<String, Object>>() {});
                event.setKey(keyData);
            }

            handleTagEvent(event);

        } catch (JsonProcessingException e) {
            log.error("Error parsing tag CDC event: {}", messageValue, e);
        } catch (Exception e) {
            log.error("Error processing tag CDC event: {}", messageValue, e);
        }
    }

    @KafkaListener(topics = {"category-events"}, groupId = "category-document-group")
    public void consumeCategoryEvents(
            @Payload String messageValue,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {

        try {
            log.debug("Received category CDC event - Key: {}, Value: {}", messageKey, messageValue);

            // 값 데이터 파싱
            CdcEvent event = objectMapper.readValue(messageValue, CdcEvent.class);

            // 키 데이터 파싱 및 설정
            if (messageKey != null && !messageKey.isEmpty()) {
                Map<String, Object> keyData = objectMapper.readValue(messageKey,
                        new TypeReference<Map<String, Object>>() {});
                event.setKey(keyData);
            }

            handleCategoryEvent(event);

        } catch (JsonProcessingException e) {
            log.error("Error parsing category CDC event: {}", messageValue, e);
        } catch (Exception e) {
            log.error("Error processing category CDC event: {}", messageValue, e);
        }
    }

    private void handleSellerEvent(CdcEvent event) {
        Map<String, Object> data;
        Long sellerId;

        if (event.isDelete()) {
            // 삭제 이벤트 처리
            data = event.getBeforeData();
            if (data == null || !data.containsKey("id")) {
                return;
            }

            sellerId = getLongValue(data, "id");
            sellerDocumentRepository.deleteById(sellerId);
            log.info("Deleted seller document with ID: {}", sellerId);
            return;
        }

        // 생성 또는 업데이트 이벤트 처리
        data = event.getAfterData();
        if (data == null || !data.containsKey("id")) {
            return;
        }

        sellerId = getLongValue(data, "id");

        // 판매자 문서 생성 또는 업데이트
        SellerDocument document = SellerDocument.builder()
                .id(sellerId)
                .name(getStringValue(data, "name"))
                .description(getStringValue(data, "description"))
                .logoUrl(getStringValue(data, "logo_url"))
                .rating(getBigDecimalValue(data, "rating"))
                .contactEmail(getStringValue(data, "contact_email"))
                .contactPhone(getStringValue(data, "contact_phone"))
                .createdAt(parseTimestampToLocalDateTime(data.get("created_at")))
                .build();

        sellerDocumentRepository.save(document);
        log.info("Saved seller document with ID: {}", sellerId);
    }

    private void handleBrandEvent(CdcEvent event) {
        Map<String, Object> data;
        Long brandId;

        if (event.isDelete()) {
            // 삭제 이벤트 처리
            data = event.getBeforeData();
            if (data == null || !data.containsKey("id")) {
                return;
            }

            brandId = getLongValue(data, "id");
            brandDocumentRepository.deleteById(brandId);
            log.info("Deleted brand document with ID: {}", brandId);
            return;
        }

        // 생성 또는 업데이트 이벤트 처리
        data = event.getAfterData();
        if (data == null || !data.containsKey("id")) {
            return;
        }

        brandId = getLongValue(data, "id");

        // 브랜드 문서 생성 또는 업데이트
        BrandDocument document = BrandDocument.builder()
                .id(brandId)
                .name(getStringValue(data, "name"))
                .slug(getStringValue(data, "slug"))
                .description(getStringValue(data, "description"))
                .logoUrl(getStringValue(data, "logo_url"))
                .website(getStringValue(data, "website"))
                .build();

        brandDocumentRepository.save(document);
        log.info("Saved brand document with ID: {}", brandId);
    }

    private void handleTagEvent(CdcEvent event) {
        Map<String, Object> data;
        Long tagId;

        if (event.isDelete()) {
            // 삭제 이벤트 처리
            data = event.getBeforeData();
            if (data == null || !data.containsKey("id")) {
                return;
            }

            tagId = getLongValue(data, "id");
            tagDocumentRepository.deleteById(tagId);
            log.info("Deleted tag document with ID: {}", tagId);
            return;
        }

        // 생성 또는 업데이트 이벤트 처리
        data = event.getAfterData();
        if (data == null || !data.containsKey("id")) {
            return;
        }

        tagId = getLongValue(data, "id");

        // 태그 문서 생성 또는 업데이트
        TagDocument document = TagDocument.builder()
                .id(tagId)
                .name(getStringValue(data, "name"))
                .slug(getStringValue(data, "slug"))
                .build();

        tagDocumentRepository.save(document);
        log.info("Saved tag document with ID: {}", tagId);
    }

    private void handleCategoryEvent(CdcEvent event) {
        Map<String, Object> data;
        Long categoryId;

        if (event.isDelete()) {
            // 삭제 이벤트 처리
            data = event.getBeforeData();
            if (data == null || !data.containsKey("id")) {
                return;
            }

            categoryId = getLongValue(data, "id");
            categoryDocumentRepository.deleteById(categoryId);

            // 하위 카테고리도 모두 삭제 필요 여부 검토 (옵션)
            log.info("Deleted category document with ID: {}", categoryId);
            return;
        }

        // 생성 또는 업데이트 이벤트 처리
        data = event.getAfterData();
        if (data == null || !data.containsKey("id")) {
            return;
        }

        categoryId = getLongValue(data, "id");

        // MongoDB에서 기존 문서 조회 또는 새 문서 생성
        CategoryDocument document = categoryDocumentRepository.findById(categoryId)
                .orElse(CategoryDocument.builder()
                        .id(categoryId)
                        .children(new ArrayList<>())
                        .build());

        // 기본 정보 업데이트
        document.setName(getStringValue(data, "name"));
        document.setSlug(getStringValue(data, "slug"));
        document.setDescription(getStringValue(data, "description"));
        document.setLevel(getIntegerValue(data, "level"));
        document.setImageUrl(getStringValue(data, "image_url"));

        // 부모 카테고리 정보 설정
        Long parentId = getLongValue(data, "parent_id");
        if (parentId != null) {
            // 부모 카테고리 정보 조회 및 설정
            Optional<CategoryDocument> parentCategory = categoryDocumentRepository.findById(parentId);

            if (parentCategory.isPresent()) {
                CategoryDocument.ParentCategory parent = CategoryDocument.ParentCategory.builder()
                        .id(parentId)
                        .name(parentCategory.get().getName())
                        .slug(parentCategory.get().getSlug())
                        .build();
                document.setParent(parent);

                // 부모 카테고리의 children 목록에 현재 카테고리 추가
                updateParentCategoryChildren(parentId, categoryId, document.getName(), document.getSlug(), document.getLevel());
            } else {
                // 부모 카테고리가 아직 MongoDB에 동기화되지 않은 경우
                // JPA 저장소에서 기본 정보만 가져와서 설정
                log.warn("Parent category {} not found in MongoDB, fetching from JPA repository", parentId);
                categoryRepository.findById(parentId).ifPresent(jpaCategory -> {
                    CategoryDocument.ParentCategory parent = CategoryDocument.ParentCategory.builder()
                            .id(parentId)
                            .name(jpaCategory.getName())
                            .slug(jpaCategory.getSlug())
                            .build();
                    document.setParent(parent);
                });
            }
        } else {
            document.setParent(null);
        }

        // 문서 저장
        categoryDocumentRepository.save(document);
        log.info("Saved category document with ID: {}", categoryId);
    }

    private void updateParentCategoryChildren(Long parentId, Long childId, String childName, String childSlug, Integer childLevel) {
        // 부모 카테고리 조회
        Optional<CategoryDocument> parentOpt = categoryDocumentRepository.findById(parentId);
        if (parentOpt.isEmpty()) {
            return;
        }

        CategoryDocument parent = parentOpt.get();
        List<CategoryDocument.ChildCategory> children = parent.getChildren();

        // 기존 자식 카테고리 인덱스 찾기
        int existingIndex = -1;
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getId().equals(childId)) {
                existingIndex = i;
                break;
            }
        }

        // 자식 카테고리 업데이트 또는 추가
        CategoryDocument.ChildCategory childInfo = CategoryDocument.ChildCategory.builder()
                .id(childId)
                .name(childName)
                .slug(childSlug)
                .level(childLevel)
                .build();

        if (existingIndex >= 0) {
            children.set(existingIndex, childInfo);
        } else {
            children.add(childInfo);
        }

        // 부모 카테고리 저장
        categoryDocumentRepository.save(parent);
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

    private LocalDateTime parseTimestampToLocalDateTime(Object timestamp) {
        if (timestamp == null) {
            return null;
        }

        if (timestamp instanceof String) {
            try {
                // PostgreSQL timestamp 형식 파싱
                return LocalDateTime.parse((String) timestamp);
            } catch (Exception e) {
                try {
                    // 다른 ISO 형식이나 특수 형식 파싱 시도
                    return LocalDateTime.parse((String) timestamp, DateTimeFormatter.ISO_DATE_TIME);
                } catch (Exception e2) {
                    log.warn("Failed to parse timestamp string: {}", timestamp);
                    return null;
                }
            }
        } else if (timestamp instanceof Number) {
            try {
                // 마이크로초 단위를 밀리초로 변환 (1/1000)
                long microseconds = ((Number) timestamp).longValue();
                long milliseconds = microseconds / 1000;

                return LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(milliseconds),
                        ZoneId.systemDefault());
            } catch (Exception e) {
                log.warn("Failed to parse timestamp number: {}", timestamp);
                return null;
            }
        }

        return null;
    }
}