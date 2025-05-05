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

import java.util.*;

@Component
@Slf4j
public class ProductCategorySMEHandler extends ProductSearchModelEventHandler {

    private final ProductSearchRepository productSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public ProductCategorySMEHandler(
            ObjectMapper objectMapper,
            ProductSearchRepository productSearchRepository,
            ElasticsearchOperations elasticsearchOperations) {
        super(objectMapper);
        this.productSearchRepository = productSearchRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    protected String getSupportedTable() {
        return "product_categories";
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
            log.debug("Partially updated product categories: {}", productId);
        } catch (Exception e) {
            log.error("Error updating product categories {}: {}", productId, e.getMessage());
        }
    }
}