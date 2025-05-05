package com.preonboarding.challenge.query.sync.handler.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.document.ProductDocument;
import com.preonboarding.challenge.query.document.ProductDocumentRepository;
import com.preonboarding.challenge.query.sync.CdcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class ProductDMEHandler extends ProductDocumentModelEventHandler {

    private final ProductDocumentRepository productDocumentRepository;

    public ProductDMEHandler(ObjectMapper objectMapper, ProductDocumentRepository productDocumentRepository) {
        super(objectMapper);
        this.productDocumentRepository = productDocumentRepository;
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
        productDocumentRepository.deleteById(productId);
        log.info("Deleted product document: {}", productId);
    }

    private void handleCreateOrUpdate(CdcEvent event) {
        // 기존 로직 구현
        Map<String, Object> data = event.getAfterData();
        if (data == null || !data.containsKey("id")) {
            return;
        }

        Long productId = getLongValue(data, "id");

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
        log.info("Saved product document: {}", productId);
    }
}