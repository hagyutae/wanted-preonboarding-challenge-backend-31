package com.preonboarding.challenge.service.query.sync.handler.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.service.query.entity.ProductDocument;
import com.preonboarding.challenge.service.query.repository.ProductDocumentRepository;
import com.preonboarding.challenge.service.query.sync.CdcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class ProductDetailDMEHandler extends ProductDocumentModelEventHandler {

    private final ProductDocumentRepository productDocumentRepository;

    public ProductDetailDMEHandler(ObjectMapper objectMapper, ProductDocumentRepository productDocumentRepository) {
        super(objectMapper);
        this.productDocumentRepository = productDocumentRepository;
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
            detail.setWeight(getBigDecimalValue(data, "weight").doubleValue());
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
        log.info("Updated product detail information for product ID: {}", productId);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> parseJsonString(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return new java.util.HashMap<>();
        }

        try {
            return objectMapper.readValue(jsonString, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            log.warn("Failed to parse JSON string: {}", jsonString);
            return new java.util.HashMap<>();
        }
    }
}