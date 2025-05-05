package com.preonboarding.challenge.query.sync.handler.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.document.ProductDocument;
import com.preonboarding.challenge.query.document.ProductDocumentRepository;
import com.preonboarding.challenge.query.sync.CdcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class ProductPriceDMEHandler extends ProductDocumentModelEventHandler {

    private final ProductDocumentRepository productDocumentRepository;

    public ProductPriceDMEHandler(ObjectMapper objectMapper, ProductDocumentRepository productDocumentRepository) {
        super(objectMapper);
        this.productDocumentRepository = productDocumentRepository;
    }

    @Override
    protected String getSupportedTable() {
        return "product_prices";
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
            log.warn("Product document not found for price update: {}", productId);
            return;
        }

        ProductDocument document = optionalDocument.get();

        // 삭제 이벤트 처리
        if (event.isDelete()) {
            document.setPrice(null);
            productDocumentRepository.save(document);
            return;
        }

        // 가격 정보 업데이트
        ProductDocument.PriceInfo priceInfo = document.getPrice();
        if (priceInfo == null) {
            priceInfo = ProductDocument.PriceInfo.builder().build();
            document.setPrice(priceInfo);
        }

        if (data.containsKey("base_price")) {
            priceInfo.setBasePrice(getBigDecimalValue(data, "base_price"));
        }

        if (data.containsKey("sale_price")) {
            priceInfo.setSalePrice(getBigDecimalValue(data, "sale_price"));
        }

        if (data.containsKey("cost_price")) {
            priceInfo.setCostPrice(getBigDecimalValue(data, "cost_price"));
        }

        if (data.containsKey("currency")) {
            priceInfo.setCurrency(getStringValue(data, "currency"));
        }

        if (data.containsKey("tax_rate")) {
            priceInfo.setTaxRate(getBigDecimalValue(data, "tax_rate"));
        }

        // 할인율 계산
        if (priceInfo.getBasePrice() != null && priceInfo.getSalePrice() != null &&
                priceInfo.getBasePrice().compareTo(BigDecimal.ZERO) > 0) {

            if (priceInfo.getSalePrice().compareTo(priceInfo.getBasePrice()) >= 0) {
                priceInfo.setDiscountPercentage(0);
            } else {
                BigDecimal discount = priceInfo.getBasePrice().subtract(priceInfo.getSalePrice());
                BigDecimal percentage = discount.multiply(new BigDecimal("100")).divide(priceInfo.getBasePrice(), 0, BigDecimal.ROUND_HALF_UP);
                priceInfo.setDiscountPercentage(percentage.intValue());
            }
        }

        productDocumentRepository.save(document);
        log.info("Updated product price information for product ID: {}", productId);
    }
}