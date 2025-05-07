package wanted.domain.product.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import wanted.domain.product.entity.ProductDetail;

import java.math.BigDecimal;

public record ProductDetailInfoResponse(
        BigDecimal weight,
        Dimensions dimensions,
        String materials,
        String countryOfOrigin,
        String warrantyInfo,
        String careInstructions,
        AdditionalInfo additionalInfo
) {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ProductDetailInfoResponse of(ProductDetail entity) {
        return new ProductDetailInfoResponse(
                entity.getWeight() != null ? BigDecimal.valueOf(entity.getWeight()) : null,
                fromJson(entity.getDimensions(), Dimensions.class),
                entity.getMaterials(),
                entity.getCountryOfOrigin(),
                entity.getWarrantyInfo(),
                entity.getCareInstructions(),
                fromJson(entity.getAdditionalInfo(), AdditionalInfo.class)
        );
    }

    private static <T> T fromJson(String json, Class<T> type) {
        if (json == null) return null;
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 역직렬화 실패", e);
        }
    }

    public record Dimensions(
            @JsonProperty("width") Integer width,
            @JsonProperty("height") Integer height,
            @JsonProperty("depth") Integer depth
    ) {}

    public record AdditionalInfo(
            @JsonProperty("assembly_required") Boolean assemblyRequired,
            @JsonProperty("assembly_time") String assemblyTime
    ) {}
}
