package com.preonboarding.challenge.service.query.sync.handler.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.service.query.entity.ProductDocument;
import com.preonboarding.challenge.service.query.repository.ProductDocumentRepository;
import com.preonboarding.challenge.service.repository.ProductOptionRepository;
import com.preonboarding.challenge.service.query.sync.CdcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class ProductImageDMEHandler extends ProductDocumentModelEventHandler {

    private final ProductDocumentRepository productDocumentRepository;
    private final ProductOptionRepository optionRepository;

    public ProductImageDMEHandler(
            ObjectMapper objectMapper,
            ProductDocumentRepository productDocumentRepository,
            ProductOptionRepository optionRepository) {
        super(objectMapper);
        this.productDocumentRepository = productDocumentRepository;
        this.optionRepository = optionRepository;
    }

    @Override
    protected String getSupportedTable() {
        return "product_images";
    }

    @Override
    public void handle(CdcEvent event) {
        Map<String, Object> data;
        Long imageId;
        Long productId;
        Long optionId;

        if (event.isDelete()) {
            data = event.getBeforeData();
        } else {
            data = event.getAfterData();
        }

        if (data == null) {
            return;
        }

        imageId = getLongValue(data, "id");

        // 상품 ID 직접 존재하는 경우
        if (data.containsKey("product_id")) {
            productId = getLongValue(data, "product_id");
        }
        // 옵션 ID를 통해 상품 ID 조회해야 하는 경우
        else if (data.containsKey("option_id") && data.get("option_id") != null) {
            optionId = getLongValue(data, "option_id");
            productId = optionRepository.findProductIdByOptionId(optionId);
            if (productId == null) {
                log.warn("Product not found for option: {}", optionId);
                return;
            }
        } else {
            log.warn("Cannot determine product ID for image event");
            return;
        }

        // 상품 문서 조회
        Optional<ProductDocument> optionalDocument = productDocumentRepository.findById(productId);
        if (optionalDocument.isEmpty()) {
            log.warn("Product document not found for image update: {}", productId);
            return;
        }

        ProductDocument document = optionalDocument.get();
        optionId = getLongValue(data, "option_id");

        // 삭제 이벤트 처리
        if (event.isDelete()) {
            // 옵션 이미지인 경우
            if (optionId != null) {
                for (ProductDocument.OptionGroup group : document.getOptionGroups()) {
                    for (ProductDocument.Option option : group.getOptions()) {
                        if (option.getId().equals(optionId)) {
                            option.getImages().removeIf(img -> img.getId().equals(imageId));
                            break;
                        }
                    }
                }
            }
            // 상품 이미지인 경우
            else {
                document.getImages().removeIf(img -> img.getId().equals(imageId));
            }

            productDocumentRepository.save(document);
            log.info("Removed image {} from product {}", imageId, productId);
            return;
        }

        // 이미지 정보 생성
        ProductDocument.Image image = ProductDocument.Image.builder()
                .id(imageId)
                .url(getStringValue(data, "url"))
                .altText(getStringValue(data, "alt_text"))
                .isPrimary(getBooleanValue(data, "is_primary") != null ? getBooleanValue(data, "is_primary") : false)
                .displayOrder(getIntegerValue(data, "display_order"))
                .optionId(optionId)
                .build();

        // 옵션 이미지인 경우
        if (optionId != null) {
            boolean imageAdded = false;

            for (ProductDocument.OptionGroup group : document.getOptionGroups()) {
                for (ProductDocument.Option option : group.getOptions()) {
                    if (option.getId().equals(optionId)) {
                        // 기존 이미지 찾기
                        Optional<ProductDocument.Image> existingImage = option.getImages().stream()
                                .filter(img -> img.getId().equals(imageId))
                                .findFirst();

                        if (existingImage.isPresent()) {
                            // 기존 이미지 업데이트
                            int index = option.getImages().indexOf(existingImage.get());
                            option.getImages().set(index, image);
                        } else {
                            // 새 이미지 추가
                            option.getImages().add(image);
                        }

                        imageAdded = true;
                        break;
                    }
                }
                if (imageAdded) break;
            }
        }
        // 상품 이미지인 경우
        else {
            // 이미지 목록 초기화 확인
            if (document.getImages() == null) {
                document.setImages(new ArrayList<>());
            }

            // 기존 이미지 찾기
            Optional<ProductDocument.Image> existingImage = document.getImages().stream()
                    .filter(img -> img.getId().equals(imageId))
                    .findFirst();

            if (existingImage.isPresent()) {
                // 기존 이미지 업데이트
                int index = document.getImages().indexOf(existingImage.get());
                document.getImages().set(index, image);
            } else {
                // 새 이미지 추가
                document.getImages().add(image);
            }
        }

        productDocumentRepository.save(document);
        log.info("Updated image for product ID: {}, image ID: {}", productId, imageId);
    }
}