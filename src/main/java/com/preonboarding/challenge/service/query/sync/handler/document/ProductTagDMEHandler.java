package com.preonboarding.challenge.service.query.sync.handler.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.service.query.entity.ProductDocument;
import com.preonboarding.challenge.service.query.repository.ProductDocumentRepository;
import com.preonboarding.challenge.service.query.sync.CdcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class ProductTagDMEHandler extends ProductDocumentModelEventHandler {

    private final ProductDocumentRepository productDocumentRepository;

    public ProductTagDMEHandler(ObjectMapper objectMapper, ProductDocumentRepository productDocumentRepository) {
        super(objectMapper);
        this.productDocumentRepository = productDocumentRepository;
    }

    @Override
    protected String getSupportedTable() {
        return "product_tags";
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

        log.info("Updated product tag mapping for product ID: {}, tag ID: {}", productId, tagId);
    }
}