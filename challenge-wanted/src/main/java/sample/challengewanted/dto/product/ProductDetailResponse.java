package sample.challengewanted.dto.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductDetailResponse {

    private Double weight;
    private DimensionsResponse dimensions;
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    private AdditionalInfoResponse additionalInfo;

    @QueryProjection
    public ProductDetailResponse(Double weight,
                                 String dimensionsJson,
                                 String materials,
                                 String countryOfOrigin,
                                 String warrantyInfo,
                                 String careInstructions,
                                 String additionalInfoJson) {

        this.weight = weight;
        this.materials = materials;
        this.countryOfOrigin = countryOfOrigin;
        this.warrantyInfo = warrantyInfo;
        this.careInstructions = careInstructions;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (dimensionsJson != null && !dimensionsJson.isBlank()) {
                this.dimensions = objectMapper.readValue(dimensionsJson, DimensionsResponse.class);
            }

            if (additionalInfoJson != null && !additionalInfoJson.isBlank()) {
                this.additionalInfo = objectMapper.readValue(additionalInfoJson, AdditionalInfoResponse.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ product_detail JSON 파싱 실패: " + e.toString(), e);
        }
    }
}