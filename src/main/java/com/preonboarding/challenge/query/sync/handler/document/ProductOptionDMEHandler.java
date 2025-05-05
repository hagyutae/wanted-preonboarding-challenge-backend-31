package com.preonboarding.challenge.query.sync.handler.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.document.ProductDocument;
import com.preonboarding.challenge.query.document.ProductDocumentRepository;
import com.preonboarding.challenge.repository.ProductOptionGroupRepository;
import com.preonboarding.challenge.repository.projection.OptionGroupWithProductProjection;
import com.preonboarding.challenge.query.sync.CdcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class ProductOptionDMEHandler extends ProductDocumentModelEventHandler {

    private final ProductDocumentRepository productDocumentRepository;
    private final ProductOptionGroupRepository optionGroupRepository;

    public ProductOptionDMEHandler(
            ObjectMapper objectMapper,
            ProductDocumentRepository productDocumentRepository,
            ProductOptionGroupRepository optionGroupRepository) {
        super(objectMapper);
        this.productDocumentRepository = productDocumentRepository;
        this.optionGroupRepository = optionGroupRepository;
    }

    @Override
    protected String getSupportedTable() {
        return "product_options";
    }

    @Override
    public void handle(CdcEvent event) {
        Map<String, Object> data;
        Long optionGroupId;
        Long optionId;

        if (event.isDelete()) {
            data = event.getBeforeData();
        } else {
            data = event.getAfterData();
        }

        if (data == null || !data.containsKey("option_group_id")) {
            return;
        }

        optionGroupId = getLongValue(data, "option_group_id");
        optionId = getLongValue(data, "id");

        // 해당 옵션 그룹을 가진 상품 ID 조회
        Optional<OptionGroupWithProductProjection> optionGroupProjection =
                optionGroupRepository.findOptionGroupWithProductProjection(optionGroupId);

        if (optionGroupProjection.isEmpty()) {
            log.warn("Option group not found: {}", optionGroupId);
            return;
        }

        Long productId = optionGroupProjection.get().getProductId();

        // 상품 문서 조회
        Optional<ProductDocument> optionalDocument = productDocumentRepository.findById(productId);
        if (optionalDocument.isEmpty()) {
            log.warn("Product document not found for option update: {}", productId);
            return;
        }

        ProductDocument document = optionalDocument.get();

        // 해당 옵션 그룹 찾기
        Optional<ProductDocument.OptionGroup> optionGroupOpt = document.getOptionGroups().stream()
                .filter(group -> group.getId().equals(optionGroupId))
                .findFirst();

        if (optionGroupOpt.isEmpty()) {
            log.warn("Option group {} not found in product {}", optionGroupId, productId);
            return;
        }

        ProductDocument.OptionGroup optionGroup = optionGroupOpt.get();

        // 삭제 이벤트 처리
        if (event.isDelete()) {
            optionGroup.getOptions().removeIf(option -> option.getId().equals(optionId));
            productDocumentRepository.save(document);
            log.info("Removed option {} from option group {}", optionId, optionGroupId);
            return;
        }

        // 기존 옵션 찾거나 새로 생성
        Optional<ProductDocument.Option> existingOption = optionGroup.getOptions().stream()
                .filter(option -> option.getId().equals(optionId))
                .findFirst();

        ProductDocument.Option option;
        if (existingOption.isPresent()) {
            option = existingOption.get();
        } else {
            option = ProductDocument.Option.builder()
                    .id(optionId)
                    .images(new ArrayList<>())
                    .build();
            optionGroup.getOptions().add(option);
        }

        // 옵션 정보 업데이트
        if (data.containsKey("name")) {
            option.setName(getStringValue(data, "name"));
        }

        if (data.containsKey("additional_price")) {
            option.setAdditionalPrice(getBigDecimalValue(data, "additional_price"));
        }

        if (data.containsKey("sku")) {
            option.setSku(getStringValue(data, "sku"));
        }

        if (data.containsKey("stock")) {
            option.setStock(getIntegerValue(data, "stock"));
        }

        if (data.containsKey("display_order")) {
            option.setDisplayOrder(getIntegerValue(data, "display_order"));
        }

        productDocumentRepository.save(document);
        log.info("Updated option for product ID: {}, option ID: {}", productId, optionId);
    }
}