package com.preonboarding.challenge.service.query.sync.handler.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.service.query.entity.ProductSearchDocument;
import com.preonboarding.challenge.service.query.repository.ProductSearchRepository;
import com.preonboarding.challenge.service.query.sync.CdcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class ProductSMEHandler extends ProductSearchModelEventHandler {

    private final ProductSearchRepository productSearchRepository;

    public ProductSMEHandler(ObjectMapper objectMapper, ProductSearchRepository productSearchRepository) {
        super(objectMapper);
        this.productSearchRepository = productSearchRepository;
    }

    @Override
    protected String getSupportedTable() {
        return "products";
    }

    @Override
    public void handle(CdcEvent event) {
        if (event.isDelete()) {
            handleDelete(event);
        } else {
            handleCreateOrUpdate(event);
        }
    }

    private void handleDelete(CdcEvent event) {
        Map<String, Object> data = event.getBeforeData();
        if (data == null || !data.containsKey("id")) {
            return;
        }

        Long productId = getLongValue(data, "id");
        productSearchRepository.deleteById(productId);
        log.info("Deleted product document: {}", productId);
    }

    private void handleCreateOrUpdate(CdcEvent event) {
        Map<String, Object> data = event.getAfterData();
        if (data == null || !data.containsKey("id")) {
            return;
        }

        Long productId = getLongValue(data, "id");

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
        log.info("Saved product document: {}", productId);
    }
}