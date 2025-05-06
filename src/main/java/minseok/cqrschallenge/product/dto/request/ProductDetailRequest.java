package minseok.cqrschallenge.product.dto.request;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import minseok.cqrschallenge.product.entity.Dimensions;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailRequest {
    private Double weight;
    private Dimensions dimensions;
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    private Map<String, Object> additionalInfo;
}
