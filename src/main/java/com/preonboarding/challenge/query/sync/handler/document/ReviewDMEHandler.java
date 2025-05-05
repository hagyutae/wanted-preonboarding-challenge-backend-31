package com.preonboarding.challenge.query.sync.handler.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.document.ProductDocument;
import com.preonboarding.challenge.query.document.ProductDocumentRepository;
import com.preonboarding.challenge.query.sync.CdcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class ReviewDMEHandler extends ProductDocumentModelEventHandler {

    private final ProductDocumentRepository productDocumentRepository;

    public ReviewDMEHandler(ObjectMapper objectMapper, ProductDocumentRepository productDocumentRepository) {
        super(objectMapper);
        this.productDocumentRepository = productDocumentRepository;
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
        log.info("Updated product rating information for product ID: {}", productId);
    }
}