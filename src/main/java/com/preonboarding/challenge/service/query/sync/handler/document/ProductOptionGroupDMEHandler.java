package com.preonboarding.challenge.service.query.sync.handler.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.service.query.entity.ProductDocument;
import com.preonboarding.challenge.service.query.repository.ProductDocumentRepository;
import com.preonboarding.challenge.service.repository.ProductOptionGroupRepository;
import com.preonboarding.challenge.service.query.sync.CdcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class ProductOptionGroupDMEHandler extends ProductDocumentModelEventHandler {

    private final ProductDocumentRepository productDocumentRepository;
    private final ProductOptionGroupRepository optionGroupRepository;

    public ProductOptionGroupDMEHandler(
            ObjectMapper objectMapper,
            ProductDocumentRepository productDocumentRepository,
            ProductOptionGroupRepository optionGroupRepository) {
        super(objectMapper);
        this.productDocumentRepository = productDocumentRepository;
        this.optionGroupRepository = optionGroupRepository;
    }

    @Override
    protected String getSupportedTable() {
        return "product_option_groups";
    }

    @Override
    public void handle(CdcEvent event) {
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
            log.info("Removed option group {} from product {}", optionGroupId, productId);
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
        log.info("Updated option group for product ID: {}, option group ID: {}", productId, optionGroupId);
    }
}