package com.preonboarding.challenge.query.sync.handler.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.document.ProductDocument;
import com.preonboarding.challenge.query.document.ProductDocumentRepository;
import com.preonboarding.challenge.query.sync.CdcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class ProductCategoryDMEHandler extends ProductDocumentModelEventHandler {

    private final ProductDocumentRepository productDocumentRepository;

    public ProductCategoryDMEHandler(ObjectMapper objectMapper, ProductDocumentRepository productDocumentRepository) {
        super(objectMapper);
        this.productDocumentRepository = productDocumentRepository;
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
        log.info("Updated product category mapping for product ID: {}, category ID: {}", productId, categoryId);
    }
}