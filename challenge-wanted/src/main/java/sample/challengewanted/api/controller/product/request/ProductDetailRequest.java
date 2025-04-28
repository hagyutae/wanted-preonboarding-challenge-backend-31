package sample.challengewanted.api.controller.product.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
public class ProductDetailRequest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Double weight;
    private Dimensions dimensions;
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    private Map<String, Object> additionalInfo;

    @Builder
    private ProductDetailRequest(Double weight, Dimensions dimensions, String materials,
                                 String countryOfOrigin, String warrantyInfo, String careInstructions,
                                 Map<String, Object> additionalInfo) {
        this.weight = weight;
        this.dimensions = dimensions;
        this.materials = materials;
        this.countryOfOrigin = countryOfOrigin;
        this.warrantyInfo = warrantyInfo;
        this.careInstructions = careInstructions;
        this.additionalInfo = additionalInfo;
    }

    public String getDimensionsAsJson() {
        try {
            return objectMapper.writeValueAsString(this.dimensions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("AdditionalInfo 변환 실패", e);
        }
    }

    public String getAdditionalInfoAsJson() {
        try {
            return objectMapper.writeValueAsString(this.additionalInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("AdditionalInfo 변환 실패", e);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Dimensions {
        private Double width;
        private Double height;
        private Double depth;

        @Builder
        private Dimensions(Double width, Double height, Double depth) {
            this.width = width;
            this.height = height;
            this.depth = depth;
        }
    }
}