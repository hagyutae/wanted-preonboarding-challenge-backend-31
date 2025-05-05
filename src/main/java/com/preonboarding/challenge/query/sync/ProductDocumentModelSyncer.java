package com.preonboarding.challenge.query.sync;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.document.ProductDocument;
import com.preonboarding.challenge.query.document.ProductDocumentRepository;
import com.preonboarding.challenge.repository.ProductImageRepository;
import com.preonboarding.challenge.repository.ProductOptionGroupRepository;
import com.preonboarding.challenge.repository.ProductOptionRepository;
import com.preonboarding.challenge.repository.projection.OptionGroupWithProductProjection;
import com.preonboarding.challenge.repository.projection.ProductImageProjection;
import com.preonboarding.challenge.repository.projection.ProductOptionProjection;
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
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductDocumentModelSyncer {

    private final ObjectMapper objectMapper;
    private final ProductDocumentRepository productDocumentRepository;
    private final ProductOptionGroupRepository optionGroupRepository;
    private final ProductOptionRepository optionRepository;
    private final ProductImageRepository productImageRepository;

    @KafkaListener(topics = {"product-events"}, groupId = "product-document-group")
    public void consumeProductEvents(
            @Payload String messageValue,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey
    ) {
        try {
            // 디버깅을 위한 원본 메시지 로깅
            log.debug("Received product CDC event - Key: {}, Value: {}", messageKey, messageValue);

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
                case "product_option_groups":
                    handleProductOptionGroupEvent(event); // 추가: product_option_groups 처리
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
            productDocumentRepository.deleteById(productId);
            return;
        }

        // 생성 또는 업데이트 이벤트 처리
        data = event.getAfterData();
        if (data == null || !data.containsKey("id")) {
            return;
        }

        productId = getLongValue(data, "id");

        // 기존 문서 조회 또는 신규 생성
        ProductDocument document = productDocumentRepository.findById(productId)
                .orElse(ProductDocument.builder().id(productId).build());

        // 기본 정보 업데이트
        document.setName(getStringValue(data, "name"));
        document.setSlug(getStringValue(data, "slug"));
        document.setShortDescription(getStringValue(data, "short_description"));
        document.setFullDescription(getStringValue(data, "full_description"));
        document.setStatus(getStringValue(data, "status"));

        // 날짜 필드 처리
        if (data.containsKey("created_at")) {
            document.setCreatedAt(parseTimestampToLocalDateTime(data.get("created_at")));
        }

        if (data.containsKey("updated_at")) {
            document.setUpdatedAt(parseTimestampToLocalDateTime(data.get("updated_at")));
        }

        // 판매자 정보 설정
        if (data.containsKey("seller_id") && data.get("seller_id") != null) {
            Long sellerId = getLongValue(data, "seller_id");
            if (document.getSeller() == null) {
                document.setSeller(ProductDocument.SellerInfo.builder()
                        .id(sellerId)
                        .build());
            } else {
                document.getSeller().setId(sellerId);
            }
        }

        // 브랜드 정보 설정
        if (data.containsKey("brand_id") && data.get("brand_id") != null) {
            Long brandId = getLongValue(data, "brand_id");
            if (document.getBrand() == null) {
                document.setBrand(ProductDocument.BrandInfo.builder()
                        .id(brandId)
                        .build());
            } else {
                document.getBrand().setId(brandId);
            }
        }

        // 저장
        productDocumentRepository.save(document);
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
        Optional<ProductDocument> optionalDocument = productDocumentRepository.findById(productId);

        if (optionalDocument.isEmpty()) {
            log.warn("Product document not found for price update: {}", productId);
            return;
        }

        ProductDocument document = optionalDocument.get();

        // 삭제 이벤트 처리
        if (event.isDelete()) {
            document.setPrice(null);
            productDocumentRepository.save(document);
            return;
        }

        // 가격 정보 업데이트
        ProductDocument.PriceInfo priceInfo = document.getPrice();
        if (priceInfo == null) {
            priceInfo = ProductDocument.PriceInfo.builder().build();
            document.setPrice(priceInfo);
        }

        if (data.containsKey("base_price")) {
            priceInfo.setBasePrice(getBigDecimalValue(data, "base_price"));
        }

        if (data.containsKey("sale_price")) {
            priceInfo.setSalePrice(getBigDecimalValue(data, "sale_price"));
        }

        if (data.containsKey("cost_price")) {
            priceInfo.setCostPrice(getBigDecimalValue(data, "cost_price"));
        }

        if (data.containsKey("currency")) {
            priceInfo.setCurrency(getStringValue(data, "currency"));
        }

        if (data.containsKey("tax_rate")) {
            priceInfo.setTaxRate(getBigDecimalValue(data, "tax_rate"));
        }

        // 할인율 계산
        if (priceInfo.getBasePrice() != null && priceInfo.getSalePrice() != null &&
                priceInfo.getBasePrice().compareTo(BigDecimal.ZERO) > 0) {

            if (priceInfo.getSalePrice().compareTo(priceInfo.getBasePrice()) >= 0) {
                priceInfo.setDiscountPercentage(0);
            } else {
                BigDecimal discount = priceInfo.getBasePrice().subtract(priceInfo.getSalePrice());
                BigDecimal percentage = discount.multiply(new BigDecimal("100")).divide(priceInfo.getBasePrice(), 0, BigDecimal.ROUND_HALF_UP);
                priceInfo.setDiscountPercentage(percentage.intValue());
            }
        }

        productDocumentRepository.save(document);
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
        Optional<ProductDocument> optionalDocument = productDocumentRepository.findById(productId);

        if (optionalDocument.isEmpty()) {
            log.warn("Product document not found for detail update: {}", productId);
            return;
        }

        ProductDocument document = optionalDocument.get();

        // 삭제 이벤트 처리
        if (event.isDelete()) {
            document.setDetail(null);
            productDocumentRepository.save(document);
            return;
        }

        // 상세 정보 업데이트
        ProductDocument.ProductDetail detail = document.getDetail();
        if (detail == null) {
            detail = ProductDocument.ProductDetail.builder().build();
            document.setDetail(detail);
        }

        if (data.containsKey("weight")) {
            detail.setWeight(getDoubleValue(data, "weight"));
        }

        if (data.containsKey("dimensions")) {
            detail.setDimensions(parseJsonString(getStringValue(data, "dimensions")));
        }

        if (data.containsKey("materials")) {
            detail.setMaterials(getStringValue(data, "materials"));
        }

        if (data.containsKey("country_of_origin")) {
            detail.setCountryOfOrigin(getStringValue(data, "country_of_origin"));
        }

        if (data.containsKey("warranty_info")) {
            detail.setWarrantyInfo(getStringValue(data, "warranty_info"));
        }

        if (data.containsKey("care_instructions")) {
            detail.setCareInstructions(getStringValue(data, "care_instructions"));
        }

        if (data.containsKey("additional_info")) {
            detail.setAdditionalInfo(parseJsonString(getStringValue(data, "additional_info")));
        }

        productDocumentRepository.save(document);
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
        Boolean isPrimary = getBooleanValue(data, "is_primary");

        Optional<ProductDocument> optionalDocument = productDocumentRepository.findById(productId);

        if (optionalDocument.isEmpty()) {
            log.warn("Product document not found for category update: {}", productId);
            return;
        }

        ProductDocument document = optionalDocument.get();
        List<ProductDocument.CategoryInfo> categories = document.getCategories();

        // 삭제 이벤트 처리
        if (event.isDelete()) {
            categories.removeIf(category -> category.getId().equals(categoryId));
            productDocumentRepository.save(document);
            return;
        }

        // 기존 카테고리 찾기
        Optional<ProductDocument.CategoryInfo> existingCategory = categories.stream()
                .filter(category -> category.getId().equals(categoryId))
                .findFirst();

        if (existingCategory.isPresent()) {
            // 기존 카테고리 업데이트
            existingCategory.get().setPrimary(isPrimary != null && isPrimary);
        } else {
            // 새 카테고리 추가
            ProductDocument.CategoryInfo newCategory = ProductDocument.CategoryInfo.builder()
                    .id(categoryId)
                    .isPrimary(isPrimary != null && isPrimary)
                    .build();
            categories.add(newCategory);
        }

        productDocumentRepository.save(document);
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

        Optional<ProductDocument> optionalDocument = productDocumentRepository.findById(productId);

        if (optionalDocument.isEmpty()) {
            log.warn("Product document not found for tag update: {}", productId);
            return;
        }

        ProductDocument document = optionalDocument.get();
        List<ProductDocument.TagInfo> tags = document.getTags();

        // 삭제 이벤트 처리
        if (event.isDelete()) {
            tags.removeIf(tag -> tag.getId().equals(tagId));
            productDocumentRepository.save(document);
            return;
        }

        // 이미 존재하는 태그인지 확인
        boolean tagExists = tags.stream()
                .anyMatch(tag -> tag.getId().equals(tagId));

        // 존재하지 않는 경우에만 추가
        if (!tagExists) {
            ProductDocument.TagInfo newTag = ProductDocument.TagInfo.builder()
                    .id(tagId)
                    .build();
            tags.add(newTag);
            productDocumentRepository.save(document);
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
        Optional<ProductDocument> optionalDocument = productDocumentRepository.findById(productId);

        if (optionalDocument.isEmpty()) {
            log.warn("Product document not found for review update: {}", productId);
            return;
        }

        ProductDocument document = optionalDocument.get();

        // 평점 정보 초기화
        ProductDocument.RatingSummary ratingSummary = document.getRating();
        if (ratingSummary == null) {
            ratingSummary = ProductDocument.RatingSummary.builder()
                    .average(0.0)
                    .count(0)
                    .distribution(new HashMap<>())
                    .build();
            document.setRating(ratingSummary);
        }

        double averageRating = ratingSummary.getAverage() != null ? ratingSummary.getAverage() : 0.0;
        int reviewCount = ratingSummary.getCount() != null ? ratingSummary.getCount() : 0;
        Map<Integer, Integer> distribution = ratingSummary.getDistribution();
        if (distribution == null) {
            distribution = new HashMap<>();
            ratingSummary.setDistribution(distribution);
        }

        // 리뷰 추가, 수정, 삭제에 따른 평점 정보 업데이트
        if (event.isDelete()) {
            // 삭제된 리뷰의 평점
            Integer rating = getIntegerValue(data, "rating");

            // 리뷰 수 감소
            if (reviewCount > 0) {
                reviewCount--;
            }

            // 평점 분포 업데이트
            if (rating != null) {
                int currentCount = distribution.getOrDefault(rating, 0);
                if (currentCount > 0) {
                    distribution.put(rating, currentCount - 1);
                }
            }

            // 평균 평점 재계산 (삭제된 평점 반영)
            if (rating != null && reviewCount > 0) {
                double totalRating = averageRating * (reviewCount + 1) - rating;
                averageRating = totalRating / reviewCount;
            } else if (reviewCount == 0) {
                averageRating = 0.0;
            }

        } else {
            // 추가 또는 수정된 리뷰의 평점
            Integer rating = getIntegerValue(data, "rating");

            // 이전 데이터가 있는 경우 (수정)
            if (event.isUpdate() && event.getBeforeData() != null) {
                Integer oldRating = getIntegerValue(event.getBeforeData(), "rating");

                // 평점이 실제로 변경된 경우에만 처리
                if (oldRating != null && rating != null && !oldRating.equals(rating)) {
                    // 이전 평점 분포 업데이트
                    int oldCount = distribution.getOrDefault(oldRating, 0);
                    if (oldCount > 0) {
                        distribution.put(oldRating, oldCount - 1);
                    }

                    // 새 평점 분포 업데이트
                    int newCount = distribution.getOrDefault(rating, 0);
                    distribution.put(rating, newCount + 1);

                    // 평균 평점 재계산
                    double totalRating = averageRating * reviewCount - oldRating + rating;
                    averageRating = totalRating / reviewCount;
                }
            } else { // 새 리뷰
                // 리뷰 수 증가
                reviewCount++;

                // 평점 분포 업데이트
                if (rating != null) {
                    int currentCount = distribution.getOrDefault(rating, 0);
                    distribution.put(rating, currentCount + 1);

                    // 평균 평점 재계산 (새 평점 포함)
                    double totalRating = averageRating * (reviewCount - 1) + rating;
                    averageRating = totalRating / reviewCount;
                }
            }
        }

        // 변경 사항 적용
        ratingSummary.setAverage(averageRating);
        ratingSummary.setCount(reviewCount);
        ratingSummary.setDistribution(distribution);

        productDocumentRepository.save(document);
    }

    private void handleProductOptionGroupEvent(CdcEvent event) {
        Map<String, Object> data;
        Long productId;
        Long optionGroupId;

        if (event.isDelete()) {
            data = event.getBeforeData();
        } else {
            data = event.getAfterData();
        }

        if (data == null || !data.containsKey("product_id")) {
            return;
        }

        productId = getLongValue(data, "product_id");
        optionGroupId = getLongValue(data, "id");

        // MongoDB에서 상품 문서 조회
        Optional<ProductDocument> optionalDocument = productDocumentRepository.findById(productId);

        if (optionalDocument.isEmpty()) {
            log.warn("Product document not found for option group update: {}", productId);
            return;
        }

        ProductDocument document = optionalDocument.get();

        // 옵션 그룹 목록 초기화
        if (document.getOptionGroups() == null) {
            document.setOptionGroups(new ArrayList<>());
        }

        List<ProductDocument.OptionGroup> optionGroups = document.getOptionGroups();

        // 삭제 이벤트 처리
        if (event.isDelete()) {
            optionGroups.removeIf(group -> group.getId().equals(optionGroupId));
            productDocumentRepository.save(document);
            return;
        }

        // 기존 옵션 그룹 찾거나 새로 생성
        ProductDocument.OptionGroup optionGroup = optionGroups.stream()
                .filter(group -> group.getId().equals(optionGroupId))
                .findFirst()
                .orElse(ProductDocument.OptionGroup.builder()
                        .id(optionGroupId)
                        .options(new ArrayList<>())
                        .build());

        // 옵션 그룹 정보 업데이트
        if (data.containsKey("name")) {
            optionGroup.setName(getStringValue(data, "name"));
        }

        if (data.containsKey("display_order")) {
            optionGroup.setDisplayOrder(getIntegerValue(data, "display_order"));
        }

        // 기존 목록에 없는 경우 추가
        if (!optionGroups.contains(optionGroup)) {
            optionGroups.add(optionGroup);
        }

        productDocumentRepository.save(document);
    }

    @KafkaListener(topics = {"product-option-events"}, groupId = "product-document-group")
    public void consumeProductOptionEvents(
            @Payload String messageValue,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey
    ) {
        try {
            // 디버깅을 위한 원본 메시지 로깅
            log.debug("Received product option CDC event - Key: {}, Value: {}", messageKey, messageValue);

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
                case "product_options":
                    handleProductOptionEventWithFullUpdate(event);
                    break;
                case "product_images":
                    handleProductImageEventWithFullUpdate(event);
                    break;
                default:
                    log.debug("Ignoring option event for table: {}", table);
            }

        } catch (JsonProcessingException e) {
            log.error("Error parsing CDC event: {}", messageValue, e);
        } catch (Exception e) {
            log.error("Error processing CDC event: {}", messageValue, e);
        }
    }


    private void handleProductOptionEventWithFullUpdate(CdcEvent event) {
        Map<String, Object> data;
        Long optionGroupId;

        if (event.isDelete()) {
            data = event.getBeforeData();
        } else {
            data = event.getAfterData();
        }

        if (data == null || !data.containsKey("option_group_id")) {
            return;
        }

        optionGroupId = getLongValue(data, "option_group_id");

        // 해당 옵션 그룹을 가진 상품 ID 조회
        var optionGroupProjection = optionGroupRepository.findOptionGroupWithProductProjection(optionGroupId);
        if (optionGroupProjection.isEmpty()) {
            log.warn("Option group not found: {}", optionGroupId);
            return;
        }

        Long productId = optionGroupProjection.get().getProductId();

        // 상품의 전체 옵션 그룹 및 옵션 정보를 Projection을 통해 조회
        updateProductOptionsUsingProjections(productId);
    }

    private void handleProductImageEventWithFullUpdate(CdcEvent event) {
        Map<String, Object> data;
        Long optionId;
        Long productId;

        if (event.isDelete()) {
            data = event.getBeforeData();
        } else {
            data = event.getAfterData();
        }

        if (data == null) {
            return;
        }

        // 상품 ID 직접 존재하는 경우
        if (data.containsKey("product_id")) {
            productId = getLongValue(data, "product_id");
        }
        // 옵션 ID를 통해 상품 ID 조회
        else if (data.containsKey("option_id") && data.get("option_id") != null) {
            optionId = getLongValue(data, "option_id");
            productId = optionRepository.findProductIdByOptionId(optionId);
            if (productId == null) {
                log.warn("Product not found for option: {}", optionId);
                return;
            }
        } else {
            log.warn("Cannot determine product ID for image event");
            return;
        }

        // 상품의 전체 옵션 그룹, 옵션, 이미지 정보를 Projection을 통해 조회하여 업데이트
        updateProductOptionsUsingProjections(productId);
    }

    private void updateProductOptionsUsingProjections(Long productId) {
        // MongoDB에서 상품 문서 조회
        Optional<ProductDocument> optionalDocument = productDocumentRepository.findById(productId);

        if (optionalDocument.isEmpty()) {
            log.warn("Product document not found for option/image update: {}", productId);
            return;
        }

        ProductDocument document = optionalDocument.get();

        // Projection을 통해 옵션 그룹 정보 조회
        List<OptionGroupWithProductProjection> optionGroups = optionGroupRepository.findOptionGroupsByProductId(productId);

        // 옵션 그룹 리스트 생성
        List<ProductDocument.OptionGroup> newOptionGroups = new ArrayList<>();

        // 각 옵션 그룹을 처리
        for (var groupProjection : optionGroups) {
            ProductDocument.OptionGroup optionGroup = ProductDocument.OptionGroup.builder()
                    .id(groupProjection.getId())
                    .name(groupProjection.getName())
                    .displayOrder(groupProjection.getDisplayOrder())
                    .options(new ArrayList<>())
                    .build();

            // 각 옵션 그룹의 옵션 정보 조회
            List<ProductOptionProjection> options = optionRepository.findOptionsByOptionGroupId(groupProjection.getId());

            // 각 옵션을 처리
            for (var optionProjection : options) {
                ProductDocument.Option option = ProductDocument.Option.builder()
                        .id(optionProjection.getId())
                        .name(optionProjection.getName())
                        .additionalPrice(optionProjection.getAdditionalPrice())
                        .sku(optionProjection.getSku())
                        .stock(optionProjection.getStock())
                        .displayOrder(optionProjection.getDisplayOrder())
                        .images(new ArrayList<>())
                        .build();

                // 옵션에 연결된 이미지 처리
                List<ProductImageProjection> images = productImageRepository.findImagesByOptionId(optionProjection.getId());

                for (var imageProjection : images) {
                    ProductDocument.Image image = ProductDocument.Image.builder()
                            .id(imageProjection.getId())
                            .url(imageProjection.getUrl())
                            .altText(imageProjection.getAltText())
                            .isPrimary(imageProjection.getIsPrimary())
                            .displayOrder(imageProjection.getDisplayOrder())
                            .optionId(imageProjection.getOptionId())
                            .build();

                    option.getImages().add(image);
                }

                optionGroup.getOptions().add(option);
            }

            newOptionGroups.add(optionGroup);
        }

        // 상품에 직접 연결된 이미지도 조회
        List<ProductImageProjection> productImages = productImageRepository.findImagesByProductId(productId);
        List<ProductDocument.Image> mainImages = new ArrayList<>();

        for (var imageProjection : productImages) {
            // 옵션에 연결되지 않은 이미지만 처리 (옵션 이미지는 위에서 처리함)
            if (imageProjection.getOptionId() == null) {
                ProductDocument.Image image = ProductDocument.Image.builder()
                        .id(imageProjection.getId())
                        .url(imageProjection.getUrl())
                        .altText(imageProjection.getAltText())
                        .isPrimary(imageProjection.getIsPrimary())
                        .displayOrder(imageProjection.getDisplayOrder())
                        .optionId(null)
                        .build();

                mainImages.add(image);
            }
        }

        // 상품의 메인 이미지 업데이트 (옵션에 연결되지 않은 이미지)
        if (!mainImages.isEmpty()) {
            document.setImages(mainImages);
        }

        // 기존 옵션 그룹 정보를 새로운 정보로 교체
        document.setOptionGroups(newOptionGroups);

        // 저장
        productDocumentRepository.save(document);
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

    private Double getDoubleValue(Map<String, Object> data, String key) {
        if (data.containsKey(key) && data.get(key) != null) {
            Object value = data.get(key);
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            } else {
                try {
                    return Double.valueOf(value.toString());
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

    private Boolean getBooleanValue(Map<String, Object> data, String key) {
        if (data.containsKey(key) && data.get(key) != null) {
            Object value = data.get(key);
            if (value instanceof Boolean) {
                return (Boolean) value;
            } else {
                return "true".equalsIgnoreCase(value.toString()) ||
                        "1".equals(value.toString()) ||
                        "yes".equalsIgnoreCase(value.toString());
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
                return LocalDateTime.parse((String) timestamp);
            } catch (Exception e) {
                log.warn("Failed to parse timestamp string: {}", timestamp);
                return null;
            }
        } else if (timestamp instanceof Number) {
            try {
                // 마이크로초 단위를 밀리초로 변환 (1/1000)
                long microseconds = ((Number) timestamp).longValue();
                long milliseconds = microseconds / 1000;

                Instant instant = Instant.ofEpochMilli(milliseconds);
                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            } catch (Exception e) {
                log.warn("Failed to parse timestamp number: {}", timestamp);
                return null;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> parseJsonString(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return new HashMap<>();
        }

        try {
            return objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            log.warn("Failed to parse JSON string: {}", jsonString);
            return new HashMap<>();
        }
    }
}