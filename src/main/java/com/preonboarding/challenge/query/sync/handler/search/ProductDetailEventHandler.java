package com.preonboarding.challenge.query.sync.handler.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.sync.CdcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ProductDetailEventHandler extends ProductSearchCdcEventHandler {

    private final ElasticsearchOperations elasticsearchOperations;

    public ProductDetailEventHandler(
            ObjectMapper objectMapper,
            ElasticsearchOperations elasticsearchOperations) {
        super(objectMapper);
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    protected String getSupportedTable() {
        return "product_details";
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
            log.debug("Partially updated product detail: {}", productId);
        } catch (Exception e) {
            log.error("Error updating product detail {}: {}", productId, e.getMessage());
        }
    }
}