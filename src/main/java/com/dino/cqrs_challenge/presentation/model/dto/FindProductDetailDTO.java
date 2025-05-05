package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.ProductDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Schema(description = "상품 상세 정보")
public class FindProductDetailDTO {

    @Schema(description = "상품 식별번호")
    private Long id;

    @Schema(description = "무게(kg)")
    private BigDecimal weight;

    @Schema(description = "제품 크기")
    private Map<String, Object> dimensions;

    @Schema(description = "소재")
    private String materials;

    @Schema(description = "원산지")
    private String countryOfOrigin;

    @Schema(description = "품질 보증 정보")
    private String warrantyInfo;

    @Schema(description = "관리 방법")
    private String careInstructions;

    @Schema(description = "추가 정보")
    private Map<String, Object> additionalInfo;

    public static FindProductDetailDTO from(ProductDetail productDetail) {
        FindProductDetailDTO dto = new FindProductDetailDTO();
        dto.id = productDetail.getId();
        dto.weight = productDetail.getWeight();
        dto.dimensions = productDetail.getDimensions();
        dto.materials = productDetail.getMaterials();
        dto.countryOfOrigin = productDetail.getCountryOfOrigin();
        dto.warrantyInfo = productDetail.getWarrantyInfo();
        dto.careInstructions = productDetail.getCareInstructions();
        dto.additionalInfo = productDetail.getAdditionalInfo();
        return dto;
    }
}
