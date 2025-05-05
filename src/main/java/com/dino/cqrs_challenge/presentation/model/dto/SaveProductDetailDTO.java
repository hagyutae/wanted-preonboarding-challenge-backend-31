package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.Product;
import com.dino.cqrs_challenge.domain.entity.ProductDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Schema(description = "상품 상세 정보")
public class SaveProductDetailDTO {

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

    public ProductDetail toEntity(Product product) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct(product);
        productDetail.setWeight(this.weight);
        productDetail.setDimensions(this.dimensions);
        productDetail.setMaterials(this.materials);
        productDetail.setCountryOfOrigin(this.countryOfOrigin);
        productDetail.setWarrantyInfo(this.warrantyInfo);
        productDetail.setCareInstructions(this.careInstructions);
        productDetail.setAdditionalInfo(this.additionalInfo);

        return productDetail;
    }
}
